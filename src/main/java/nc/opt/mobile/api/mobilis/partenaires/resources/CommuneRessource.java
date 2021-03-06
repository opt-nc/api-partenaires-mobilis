package nc.opt.mobile.api.mobilis.partenaires.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import nc.opt.mobile.api.mobilis.partenaires.entity.Commune;
import nc.opt.mobile.api.mobilis.partenaires.repository.CommuneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interface REST des communes se où se trouvent des boutiques partenaires
 */
@RestController
@RequestMapping("/api/communes")
@CrossOrigin("*")
@Validated
@Tag(name = "communes", description = "Communes dans lesquelles se trouvent des boutiques partenaires Mobilis")
public class CommuneRessource extends AbstradctRessource {

    private final CommuneRepository repository;

    public CommuneRessource(CommuneRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = { "application/geo+json", "application/json" })
    @Operation(
        summary = "Retourne les communes en fonction des critères de recherche",
        description = "Ce service permet de récupérer les communes en Json ou en GeoJson pour faciliter l'intégration dans des outils de cartographie"
    )
    public List<Commune> list(@RequestParam(required = false) String nom, @RequestParam(required = false) String codePostal) {
        return repository.find(nom, codePostal);
    }

    @Operation(summary = "Retourne une commune en fonction de son code INSEE")
    @GetMapping(path = "{codeInsee}", produces = "application/json")
    public ResponseEntity<Commune> get(@PathVariable String codeInsee) {
        return repository.findById(codeInsee).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
