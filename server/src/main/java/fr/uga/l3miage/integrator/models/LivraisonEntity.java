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

    private float montant;

    private float distanceParcourue;

    private Integer tdtALAller;

    private Integer tddTheorique;

    private Integer tdmTheorique;

    private Time heureDeLivraisonTheorique;
    
    @Column(nullable=true) private Time heureDeLivraisonEffective;

    @Column(nullable=true) private Integer tdmEffectif;

    @OneToMany(mappedBy = "livraison",cascade = CascadeType.ALL)
    private Set<CommandeEntity> commandes;

    @ManyToOne
    private TourneeEntity tournee;

    public void addCommandesInLivraison(CommandeEntity commande){
        Set<CommandeEntity> commandeEntities=this.getCommandes();
        if (commandeEntities!=null ) {

            commandeEntities.add(commande);


            this.setCommandes(commandeEntities);
            commande.setLivraison(this);
        }
        else{
            Set<CommandeEntity> cmd2=new HashSet<>();
            cmd2.add(commande);
            this.setCommandes(cmd2);
            commande.setLivraison(this);
        }
    }

}
