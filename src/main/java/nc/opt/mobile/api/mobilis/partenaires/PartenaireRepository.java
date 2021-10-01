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
        nativeQuery = true,
        value = """
        SELECT p.*
            FROM Partenaire as p
            WHERE
                (:q IS NULL OR p.id IN (SELECT ft.KEYS[0] FROM FTL_SEARCH_DATA(:q, 0, 0) ft WHERE ft.table = 'PARTENAIRE'))
                AND (:point IS NULL OR (ST_INTERSECTS(position, ST_TRANSFORM(ST_SetSRID(ST_BUFFER(ST_TRANSFORM(ST_SetSRID(ST_GeomFromText(:point), 4326), 3857), :distance), 3857), 4326)) = true))
                AND (:ville IS NULL OR LOWER(p.ville) = LOWER(:ville))
                AND (:codePostal IS NULL OR p.code_postal = :codePostal)
                AND (:codeInsee IS NULL OR p.code_insee = :codeInsee)
                AND (:q IS NULL OR :q IS NOT NULL)
                AND (:point IS NULL OR :point IS NOT NULL)
                AND (:distance IS NULL OR :distance IS NOT NULL)
            ORDER BY
                (SELECT ft.score FROM FTL_SEARCH_DATA(:q, 0, 0) ft WHERE ft.table = 'PARTENAIRE' and ft.keys[0] = p.id),
                ST_MaxDistance(position, ST_SetSRID(ST_GeomFromText(:point), 4326)) ASC
        """
    )
    List<Partenaire> find(
        @Param("point") String point,
        @Param("distance") long distance,
        @Param("q") String q,
        @Param("ville") String ville,
        @Param("codePostal") String codePostal,
        @Param("codeInsee") String codeInsee
    );
}
