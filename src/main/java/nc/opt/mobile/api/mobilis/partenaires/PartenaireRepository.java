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
            FROM Partenaire as p INNER JOIN Commune as c ON c.code_insee = p.code_insee
            WHERE
                (
                    (:q IS NULL OR p.id IN (SELECT ft.KEYS[0] FROM FTL_SEARCH_DATA(:q, 0, 0) ft WHERE ft.table = 'PARTENAIRE'))
                    OR (:q IS NULL OR p.id IN (SELECT ft.KEYS[0] FROM FTL_SEARCH_DATA(:q, 0, 0) ft WHERE ft.table = 'COMMUNE'))
                )
                AND (:point IS NULL OR (ST_INTERSECTS(p.position, ST_TRANSFORM(ST_SetSRID(ST_BUFFER(ST_TRANSFORM(ST_SetSRID(ST_GeomFromText(:point), 4326), 3857), :distance), 3857), 4326)) = true))
                AND (:ville IS NULL OR LOWER(c.nom) = LOWER(:ville))
                AND (:codePostal IS NULL OR c.code_postal = :codePostal)
                AND (:codeInsee IS NULL OR p.code_insee = :codeInsee)
            ORDER BY
                (SELECT ft.score FROM FTL_SEARCH_DATA(:q, 0, 0) ft WHERE ft.table = 'PARTENAIRE' and ft.keys[0] = p.id),
                ST_MaxDistance(p.position, ST_SetSRID(ST_GeomFromText(:point), 4326)) ASC

        """
    )
    List<Partenaire> find(
        @Param("point") String point,
        @Param("distance") Integer distance,
        @Param("q") String q,
        @Param("ville") String ville,
        @Param("codePostal") String codePostal,
        @Param("codeInsee") String codeInsee
    );
}
