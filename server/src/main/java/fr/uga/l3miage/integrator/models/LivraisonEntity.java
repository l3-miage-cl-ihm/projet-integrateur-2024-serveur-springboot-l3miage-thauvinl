package fr.uga.l3miage.integrator.models;

import java.sql.Time;
import java.util.Set;
import javax.persistence.*;

import lombok.*;

import fr.uga.l3miage.integrator.models.enums.EtatDeLivraisonClass.EtatDeLivraison;
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

    private Integer tdpTheorique;
    
    private Integer tddTheorique;

    private Integer tdmTheorique;

    private Time heureDeLivraisonTheorique;
    
    @Column(nullable=true) private Time heureDeLivraisonEffective;

    @Column(nullable=true) private Integer tdmEffectif;

    @OneToMany(mappedBy = "livraison")
    private Set<CommandeEntity> commandes;

    @ManyToOne
    private TourneeEntity tournee;


}
