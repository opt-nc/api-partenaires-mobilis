package nc.opt.mobile.api.mobilis.partenaires.config;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import nc.opt.mobile.api.mobilis.partenaires.entity.Commune;
import nc.opt.mobile.api.mobilis.partenaires.entity.Partenaire;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeoJsonConfiguration implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(1, new GeoJsonHttpMessageConverter());
    }

    static class GeoJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

        private static final MediaType GEOJSON = new MediaType("application", "geo+json");

        public GeoJsonHttpMessageConverter() {
            setSupportedMediaTypes(Collections.singletonList(GEOJSON));
        }

        @Override
        public boolean canRead(Class<?> clazz, MediaType mediaType) {
            return false; // pas besoin
        }

        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            return mediaType.equals(GEOJSON);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
            if (
                object instanceof Collection &&
                type instanceof ParameterizedType parameterizedType &&
                parameterizedType.getActualTypeArguments().length == 1
            ) {
                if (parameterizedType.getActualTypeArguments()[0].equals(Commune.class)) {
                    FeatureCollection collection = new FeatureCollection();
                    collection.addAll(((Collection<Commune>) object).stream().map(this::map).toList());
                    object = collection;
                } else if (parameterizedType.getActualTypeArguments()[0].equals(Partenaire.class)) {
                    FeatureCollection collection = new FeatureCollection();
                    collection.addAll(((Collection<Partenaire>) object).stream().map(this::map).toList());
                    object = collection;
                }
            }

            super.writeInternal(object, type, outputMessage);
        }

        private Feature map(Partenaire partenaire) {
            Feature feature = new Feature();

            feature.setProperties(new LinkedHashMap<>()); // to preserve input order

            feature.setProperty("id", partenaire.getId());
            feature.setProperty("nom", partenaire.getNom());
            feature.setProperty("ridet", partenaire.getRidet());
            feature.setProperty("adresse", partenaire.getAdresse());
            feature.setProperty("quartier", partenaire.getQuartier());
            feature.setProperty("ville", partenaire.getVille());
            feature.setProperty("code_postal", partenaire.getCodePostal());
            feature.setProperty("code_insee", partenaire.getCodeInsee());
            feature.setProperty("telephone", partenaire.getTelephone());

            if (partenaire.getUrlGmaps() != null) {
                feature.setProperty("url_gmaps", partenaire.getUrlGmaps());
            }
            if (partenaire.getUrlFb() != null) {
                feature.setProperty("url_fb", partenaire.getUrlFb());
            }

            feature.setGeometry(new Point(partenaire.getPosition().getX(), partenaire.getPosition().getY()));

            return feature;
        }

        private Feature map(Commune commune) {
            Feature feature = new Feature();

            feature.setProperties(new LinkedHashMap<>()); // to preserve input order

            feature.setProperty("code_insee", commune.getCodeInsee());
            feature.setProperty("nom", commune.getNom());
            feature.setProperty("code_postal", commune.getCodePostal());

            feature.setGeometry(new Point(commune.getPosition().getX(), commune.getPosition().getY()));

            return feature;
        }
    }
}
