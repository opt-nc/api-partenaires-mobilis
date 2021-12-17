package nc.opt.mobile.api.mobilis.partenaires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nc.opt.mobile.api.mobilis.partenaires.entity.Commune;

public interface CommuneRepository extends JpaRepository<Commune, String> {
    @Query("FROM Commune WHERE (:nom IS NULL OR LOWER(nom) = LOWER(:nom)) AND (:codePostal IS NULL OR codePostal = :codePostal)")
    List<Commune> find(@Param("nom") String nom, @Param("codePostal") String codePostal);
}
