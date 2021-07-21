package nc.opt.mobile.api.mobilis.partenaires;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
    /**
     * Pour la géolocalisation : à partir du point de localisation (longitude/latitude), transformation en coordonnées
     * métriques (Mercator) pour faire un cercle sur la base d'un rayon en mètres (ST_BUFFER), ensuite retour en coodronnées
     * GPS (longitude/latitude) pour vérifier l'intersection avec les positions des boutiques partenaires
     */
    @Query("FROM Partenaire WHERE ST_INTERSECTS(position, ST_TRANSFORM(ST_SetSRID(ST_BUFFER(ST_TRANSFORM(ST_SetSRID( ST_GeomFromText(:point), 4326 ), 3857 ), :distance ), 3857 ), 4326 )) = true")
    List<Partenaire> findByPositionDistance(@Param("point") String point, @Param("distance") long distance);
}
