package nc.opt.mobile.api.mobilis.partenaires;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interface REST des boutiques partenaires
 */
@RestController()
@RequestMapping("/api/partenaires")
@CrossOrigin("*")
@Validated
public class PartenaireRessource {
    private final PartenaireRepository repository;

    public PartenaireRessource(PartenaireRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/geo+json")
    public FeatureCollection listGeoJSON(@Valid @ParameterObject RequestParams params) {
        FeatureCollection collection = new FeatureCollection();
        collection.addAll(list(params).stream().map(this::map).collect(Collectors.toList()));
        return collection;
    }

    @GetMapping(produces = "application/json")
    public List<Partenaire> list(@Valid @ParameterObject RequestParams params) {
        if (params.getNearBy() != null) {
            return repository.findByPositionDistance(
                String.format("POINT(%s %s)", params.getNearBy().getLon(), params.getNearBy().getLat()),
                params.getNearBy().getDistance());
        } else {
            return repository.findAll();
        }
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public Partenaire get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private Feature map(Partenaire partenaire) {
        Feature feature = new Feature();

        feature.setProperties(new LinkedHashMap<>()); // to preserve input order

        feature.setProperty("nom", partenaire.getNom());
        feature.setProperty("adresse", partenaire.getAdresse());
        feature.setProperty("quartier", partenaire.getQuartier());
        feature.setProperty("ville", partenaire.getVille());
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
}
