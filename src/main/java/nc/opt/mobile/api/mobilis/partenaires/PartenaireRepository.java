package nc.opt.mobile.api.mobilis.partenaires;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository Spring Boot avec accès REST
 */
@RepositoryRestResource(collectionResourceRel = "partenaire", path = "partenaires")
public interface PartenaireRepository extends PagingAndSortingRepository<Partenaire, Long> {

    /**
     * Pas de possibilité de création/modif en REST
     */
    @Override
    @RestResource(exported = false)
    <S extends Partenaire> S save(S entity);
}
