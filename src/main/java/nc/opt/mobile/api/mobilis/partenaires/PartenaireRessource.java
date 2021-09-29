package nc.opt.mobile.api.mobilis.partenaires;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
@RestController
@RequestMapping("/api/partenaires")
@CrossOrigin("*")
@Validated
@Tag(name = "partenaires", description = "Boutiques partenaires Mobilis")
public class PartenaireRessource {

    private final PartenaireRepository repository;

    public PartenaireRessource(PartenaireRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/geo+json")
    @Operation(
        summary = "Retourne les partenaires par position géographique et/ou par recherche textuelle",
        description = "Ce service permet de récupérer les partenaires en Json ou en GeoJson pour faciliter l'intégration dans des outils de cartographie.\n" +
        " - L'interrogation par position se fait en fournissant un point GPS (coordonnées WSG 84) et une distance en mètres pour définir un rayon max des boutiques partenaires qui seront retournées. À noter que tous les champs `nearBy.*` doivent être renseignés pour ce type de recherche.\n" +
        " - L'interrogation par recherche textuelle multi-critères peut être combinée à la recherche par position et se fait via le paramètre `q`."
    )
    public FeatureCollection listGeoJSON(@ParameterObject RequestParams params) {
        FeatureCollection collection = new FeatureCollection();
        collection.addAll(list(params).stream().map(this::map).collect(Collectors.toList()));
        return collection;
    }

    @GetMapping(produces = "application/json")
    @Operation(
        summary = "Retourne les partenaires par position géographique et/ou par recherche textuelle",
        description = "Ce service permet de récupérer les partenaires en Json ou en GeoJson pour faciliter l'intégration dans des outils de cartographie.\n" +
        " - L'interrogation par position se fait en fournissant un point GPS (coordonnées WSG 84) et une distance en mètres pour définir un rayon max des boutiques partenaires qui seront retournées. À noter que tous les champs `nearBy.*` doivent être renseignés pour ce type de recherche.\n" +
        " - L'interrogation par recherche textuelle multi-critères peut être combinée à la recherche par position et se fait via le paramètre `q`."
    )
    public List<Partenaire> list(@ParameterObject RequestParams p) {
        if (
            p.getNearBy() != null &&
            (p.getNearBy().getLat() == null || p.getNearBy().getLon() == null || p.getNearBy().getDistance() == null)
        ) {
            throw new BadRequestException(
                "nearBy.lon, nearBy.lat et nearBy.distance doivent être tous renseignés lors d'une recherche par proximité "
            );
        }

        if (p.getNearBy() != null && p.getQ() == null) {
            return repository.findByPositionDistance(
                String.format("POINT(%s %s)", p.getNearBy().getLon(), p.getNearBy().getLat()),
                p.getNearBy().getDistance()
            );
        } else if (p.getNearBy() != null && p.getQ() != null) {
            return repository.findByPositionDistanceWithFullTextSearch(
                String.format("POINT(%s %s)", p.getNearBy().getLon(), p.getNearBy().getLat()),
                p.getNearBy().getDistance(),
                p.getQ() + "*"
            );
        } else if (p.getQ() != null) {
            return repository.findWithFullTextSearch(p.getQ() + "*");
        } else {
            return repository.findAll();
        }
    }

    @Operation(
        summary = "Retourne un partenaire en fonction de son ID",
        description = "Ce service permet de récupérer les partenaires en Json"
    )
    @GetMapping(path = "{id}", produces = "application/json")
    public Partenaire get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex
            .getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        return errors;
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
}
