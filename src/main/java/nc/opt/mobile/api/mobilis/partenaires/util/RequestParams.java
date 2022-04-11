package nc.opt.mobile.api.mobilis.partenaires.util;

import io.swagger.v3.oas.annotations.Parameter;

public class RequestParams {

    @Parameter(description = "recherche textuelle multi-critères", example = "djilo")
    private String q;

    @Parameter(
        description = "recherche por position géographique : longitude en coordonées GPS (WSG 84) en degrés décimaux",
        example = "166.442412"
    )
    private Double nearByLon;

    @Parameter(
        description = "recherche por position géographique : latitude en coordonées GPS (WSG 84) en degrés décimaux",
        example = "-22.279575"
    )
    private Double nearByLat;

    @Parameter(
        description = "recherche por position géographique : distance en mètres pour former un rayon de recherche autour du point",
        example = "1000"
    )
    private Integer nearByDistance;

    @Parameter(description = "recherche par ville", example = "nouméa")
    private String ville;

    @Parameter(description = "recherche par code postal", example = "98800")
    private String codePostal;

    @Parameter(description = "recherche par code Insee", example = "98818")
    private String codeInsee;

    public Double getNearByLon() {
        return nearByLon;
    }

    public void setNearByLon(Double nearByLon) {
        this.nearByLon = nearByLon;
    }

    public Double getNearByLat() {
        return nearByLat;
    }

    public void setNearByLat(Double nearByLat) {
        this.nearByLat = nearByLat;
    }

    public Integer getNearByDistance() {
        return nearByDistance;
    }

    public void setNearByDistance(Integer nearByDistance) {
        this.nearByDistance = nearByDistance;
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
}
