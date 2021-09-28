package nc.opt.mobile.api.mobilis.partenaires;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
    /**
     * Pour la géolocalisation : à partir du point de localisation (longitude/latitude), transformation en coordonnées
     * métriques (Mercator SRID 3857) pour faire un cercle sur la base d'un rayon en mètres (ST_BUFFER), ensuite retour en coodronnées
     * GPS (longitude/latitude - SRID 4326) pour vérifier l'intersection avec les positions des boutiques partenaires
     */
    @Query(
        "FROM Partenaire" +
        " WHERE ST_INTERSECTS(position, ST_TRANSFORM(ST_SetSRID(ST_BUFFER(ST_TRANSFORM(ST_SetSRID(ST_GeomFromText(:point), 4326), 3857), :distance), 3857), 4326)) = true" +
        " ORDER BY ST_MaxDistance(position, ST_SetSRID(ST_GeomFromText(:point), 4326)) ASC"
    ) // du plus proche au plus éloigné du point de recherche
    List<Partenaire> findByPositionDistance(@Param("point") String point, @Param("distance") long distance);

    @Query(
        nativeQuery = true,
        value = "SELECT p.* FROM Partenaire as p, FTL_SEARCH_DATA(:q, 0, 0) FT " +
        " WHERE FT.TABLE='PARTENAIRE' AND p.ID=FT.KEYS[0] AND ST_INTERSECTS(position, ST_TRANSFORM(ST_SetSRID(ST_BUFFER(ST_TRANSFORM(ST_SetSRID(ST_GeomFromText(:point), 4326), 3857), :distance), 3857), 4326)) = true" +
        " ORDER BY FT.SCORE, ST_MaxDistance(position, ST_SetSRID(ST_GeomFromText(:point), 4326)) ASC"
    )
    List<Partenaire> findByPositionDistanceWithFullTextSearch(
        @Param("point") String point,
        @Param("distance") long distance,
        @Param("q") String q
    );

    @Query(
        nativeQuery = true,
        value = "SELECT p.* FROM Partenaire as p, FTL_SEARCH_DATA(:q, 0, 0) FT " +
        " WHERE FT.TABLE='PARTENAIRE' AND p.ID=FT.KEYS[0]" +
        " ORDER BY FT.SCORE ASC"
    )
    List<Partenaire> findWithFullTextSearch(@Param("q") String q);
}
