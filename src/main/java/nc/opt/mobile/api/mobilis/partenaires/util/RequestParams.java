package nc.opt.mobile.api.mobilis.partenaires.util;

import io.swagger.v3.oas.annotations.Parameter;

public class RequestParams {

    @Parameter(description = "recherche textuelle multi-critères", example = "djilo")
    private String q;

    private NearBy nearBy;

    @Parameter(description = "recherche par ville", example = "nouméa")
    private String ville;

    @Parameter(description = "recherche par code postal", example = "98800")
    private String codePostal;

    @Parameter(description = "recherche par code Insee", example = "98818")
    private String codeInsee;

    public NearBy getNearBy() {
        return nearBy;
    }

    public void setNearBy(NearBy nearBy) {
        this.nearBy = nearBy;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getQ() {
        return q;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCodeInsee() {
        return codeInsee;
    }

    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }

    public static class NearBy {

        @Parameter(
            description = "recherche por position géographique : longitude en coordonées GPS (WSG 84) en degrés décimaux",
            example = "166.442412"
        )
        private Double lon;

        @Parameter(
            description = "recherche por position géographique : latitude en coordonées GPS (WSG 84) en degrés décimaux",
            example = "-22.279575"
        )
        private Double lat;

        @Parameter(
            description = "recherche por position géographique : distance en mètres pour former un rayon de recherche autour du point",
            example = "1000"
        )
        private Integer distance;

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }
    }
}
