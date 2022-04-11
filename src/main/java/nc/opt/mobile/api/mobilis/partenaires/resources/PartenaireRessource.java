package nc.opt.mobile.api.mobilis.partenaires.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nc.opt.mobile.api.mobilis.partenaires.BadRequestException;
import nc.opt.mobile.api.mobilis.partenaires.entity.Partenaire;
import nc.opt.mobile.api.mobilis.partenaires.repository.PartenaireRepository;
import nc.opt.mobile.api.mobilis.partenaires.util.RequestParams;

/**
 * Interface REST des boutiques partenaires
 */
@RestController
@RequestMapping("/api/partenaires")
@CrossOrigin("*")
@Validated
@Tag(name = "partenaires", description = "Boutiques partenaires Mobilis")
public class PartenaireRessource extends AbstradctRessource {

    private final PartenaireRepository repository;

    public PartenaireRessource(PartenaireRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = { "application/geo+json", "application/json" })
    @Operation(
        summary = "Retourne les partenaires par position géographique et/ou par recherche textuelle",
        description = """
        Ce service permet de récupérer les partenaires en Json ou en GeoJson pour faciliter l'intégration dans des outils de cartographie.
         - L'interrogation par position se fait en fournissant un point GPS (coordonnées WSG 84) et une distance en mètres pour définir un rayon max des boutiques partenaires qui seront retournées. À noter que tous les champs `nearBy*` doivent être renseignés pour ce type de recherche.
         - L'interrogation par recherche textuelle multi-critères peut être combinée à la recherche par position et se fait via le paramètre `q`.
         """
    )
    public List<Partenaire> list(@ParameterObject RequestParams p) {
        List<Object> nearByParams = Arrays.asList(p.getNearByLat() ,p.getNearByLon() ,p.getNearByDistance());
        if (nearByParams.stream().anyMatch(Objects::nonNull) && !nearByParams.stream().noneMatch(Objects::isNull)) {
            throw new BadRequestException(
                "nearByLon, nearByLat et nearByDistance doivent être tous renseignés lors d'une recherche par proximité "
            );
        }

        return repository.find(
            p.getNearByLat() != null && p.getNearByLon()!= null? String.format("POINT(%s %s)", p.getNearByLon(), p.getNearByLat()) : null,
            p.getNearByDistance() ,
            p.getQ(),
            p.getVille(),
            p.getCodePostal(),
            p.getCodeInsee()
        );
    }

    @Operation(summary = "Retourne un partenaire en fonction de son ID")
    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Partenaire> get(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
