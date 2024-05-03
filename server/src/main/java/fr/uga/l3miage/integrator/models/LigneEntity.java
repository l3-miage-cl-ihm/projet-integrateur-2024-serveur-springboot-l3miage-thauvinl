package fr.uga.l3miage.integrator.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Lignes")
public class LigneEntity {
    @Id
    private String reference;

    @ManyToOne
    @JoinColumn(name = "commande_id", referencedColumnName = "reference")
    private CommandeEntity commande;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "reference")
    private ProduitEntity produit;

    @Column(name = "quantite")
    private int quantite;

    @Column(name = "montant")
    private double montant;

    @Column(name = "Option_de_montage")
    private boolean optionDeMontage;

    public void setMontant(){
        this.montant = this.quantite*this.produit.getPrix();
    }
}

