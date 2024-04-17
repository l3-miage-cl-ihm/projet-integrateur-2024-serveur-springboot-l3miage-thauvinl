package fr.uga.l3miage.integrator.models;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.uga.l3miage.integrator.models.enums.EtatDeCommandeClass.EtatDeCommande;
import lombok.Data;

@Entity
@Data
public class CommandeEntity {
    @Id 
    private String reference;

    @Enumerated(EnumType.STRING)
    private EtatDeCommande etat;

    private Date dateDeCreation;

    @Column(nullable=true) private Integer note;
    @Column(nullable=true) private String commentaire;

    private float montant;
    private Integer tddTheorique;

    private Integer tdmTheorique;
    @Column(nullable=true) private Date dateDeLivraisonEffective;

    @Column(nullable=true) private Integer dureeDeLivraison;

    @ManyToOne()
    private LivraisonEntity livraisonEntity;
    

    
}
