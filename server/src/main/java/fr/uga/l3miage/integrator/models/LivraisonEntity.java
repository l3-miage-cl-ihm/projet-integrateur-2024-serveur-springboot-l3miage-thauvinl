package fr.uga.l3miage.integrator.models;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
import lombok.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivraisonEntity {
    @Id
    private String reference;

    @Enumerated(EnumType.ORDINAL)
    private EtatDeLivraison etat;

    private Double montant;

    private Double distanceAParcourir;

    private Integer tdtALAller;

    //private Integer tddTheorique;

    private Integer tdmTheorique;

    private Time heureDeLivraisonTheorique;
    
    @Column
    private Time heureDeLivraisonEffective;

    @Column
    private Integer tdmEffectif;

    @OneToMany(mappedBy = "livraison",cascade = CascadeType.ALL)
    private Set<CommandeEntity> commandes = new HashSet<>();

    @ManyToOne
    private TourneeEntity tournee;

    public void addCommandesInLivraison(CommandeEntity commande){
        if(commande.getLivraison()!=null){
            throw new IllegalArgumentException(String.format("La commande de référence %s est déjà dans une autre tournée",commande.getReference()));
        }
        this.commandes.add(commande);
        commande.setLivraison(this);
    }

}
