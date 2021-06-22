package nc.opt.mobile.api.mobilis.partenaires;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository Spring Boot avec accès REST
 */
@RepositoryRestResource(collectionResourceRel = "partenaire", path = "partenaires")
public interface PartenaireRepository extends PagingAndSortingRepository<Partenaire, Long> {
}
