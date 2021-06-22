package nc.opt.mobile.api.mobilis.partenaires;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Représente une boutique partenaire Mobilis
 */
@Entity
public class Partenaire {
    
    @Id
    private Long id;
    private String nom;
    private String telephone;
    private String adresse;
    private String quartier;
    private String ville;

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
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
}
