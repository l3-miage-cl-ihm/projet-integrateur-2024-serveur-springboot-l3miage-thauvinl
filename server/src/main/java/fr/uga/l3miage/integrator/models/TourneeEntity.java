package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.enums.EtatsDeTournee;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourneeEntity {
    @Id
    private String reference;
    @Enumerated(EnumType.ORDINAL)
    private EtatsDeTournee etatsDeTournee;
    private String lettre;
    private Double montant;
    private Integer tempsDeMontageTheorique;
    private Integer tempsDeMontageEffectif;
    private Double distanceAParcourir;
    private Double distanceDeRetour;
    /*@OneToMany
    @JoinColumn(name="tournee_entity_reference", referencedColumnName = "reference")
    private List<LivraisonEntity> livraisonEntities;
    @OneToOne
    private CamionEntity;*/

}
