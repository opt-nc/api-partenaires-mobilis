package nc.opt.mobile.api.mobilis.partenaires;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.geo.Point;

import nc.opt.mobile.api.mobilis.partenaires.util.PointConverter;

/**
 * Représente une boutique partenaire Mobilis
 */
@Entity
public class Partenaire {

    @Id
    private Long id;
    @Column(nullable = false)
    private String nom;
    private String ridet;
    private String telephone;
    @JsonProperty("url_gmaps")
    private String urlGmaps;
    @JsonProperty("url_fb")
    private String urlFb;
    @Column(nullable = false)
    private String adresse;
    private String quartier;
    private String ville;
    private String codePostal;
    private String codeInsee;

    @Convert(converter = PointConverter.class)
    @Column(columnDefinition = "GEOMETRY", nullable = false)
    private Point position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRidet() {
        return ridet;
    }

    public void setRidet(String ridet) {
        this.ridet = ridet;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUrlGmaps() {
        return urlGmaps;
    }

    public void setUrlGmaps(String urlGmaps) {
        this.urlGmaps = urlGmaps;
    }

    public String getUrlFb() {
        return urlFb;
    }

    public void setUrlFb(String urlFb) {
        this.urlFb = urlFb;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
