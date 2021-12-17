package nc.opt.mobile.api.mobilis.partenaires;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import nc.opt.mobile.api.mobilis.partenaires.util.PointConverter;
import org.springframework.data.geo.Point;

/**
 * Représente une boutique partenaire Mobilis
 */
@Entity
public class Commune {

    @Id
    private String codeInsee;

    @Column(nullable = false)
    private String nom;

    private String codePostal;

    @Convert(converter = PointConverter.class)
    @Column(columnDefinition = "GEOMETRY", nullable = false)
    private Point position;

    public String getCodeInsee() {
        return codeInsee;
    }

    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
