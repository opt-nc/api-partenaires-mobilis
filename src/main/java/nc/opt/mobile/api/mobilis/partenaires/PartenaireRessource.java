package nc.opt.mobile.api.mobilis.partenaires;

import java.util.stream.Collectors;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interface REST des boutiques partenaires
 */
@RestController()
@RequestMapping("/api/partenaires")
@CrossOrigin("*")
public class PartenaireRessource {
    private final PartenaireRepository repository;

    public PartenaireRessource(PartenaireRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/geo+json")
    public FeatureCollection getGeoJSON() {
        FeatureCollection collection = new FeatureCollection();
        collection.addAll(repository.findAll().stream().map(this::map).collect(Collectors.toList()));
        return collection;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Partenaire> getJSON() {
        return repository.findAll();
    }

    private Feature map(Partenaire partenaire) {
        Feature feature = new Feature();

        feature.setProperty("nom", partenaire.getNom());
        feature.setProperty("adresse", partenaire.getAdresse());
        feature.setProperty("quartier", partenaire.getQuartier());
        feature.setProperty("ville", partenaire.getVille());
        feature.setProperty("telephone", partenaire.getTelephone());

        feature.setGeometry(new Point(partenaire.getPosition().getX(), partenaire.getPosition().getY()));

        return feature;
    }
}
