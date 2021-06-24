package nc.opt.mobile.api.mobilis.partenaires;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Repository Spring Boot avec accès REST
 */
@CrossOrigin("*")
@RepositoryRestResource(collectionResourceRel = "partenaire", path = "partenaires")
public interface PartenaireRepository extends PagingAndSortingRepository<Partenaire, Long> {

    /**
     * Pas de possibilité de création/modif en REST
     */
    @Override
    @RestResource(exported = false)
    <S extends Partenaire> S save(S entity);

    @Override
    @RestResource(exported = false)
    void delete(Partenaire entity);
}
