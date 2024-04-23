package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.models.enums.EtatDeCommandeClass;
import lombok.*;

import javax.persistence.*;

import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourneeEntity {
    @Id
    private String reference;
    @Enumerated(EnumType.STRING)
    private EtatDeCommandeClass.EtatsDeTournee etatsDeTournee;
    private String lettre;
    private Double montant;
    private Integer tempsDeMontageTheorique;
    private Integer tempsDeMontageEffectif;
    private Double distanceAParcourir;
    private Double distanceDeRetour;
    @OneToMany(mappedBy = "tournee")
    private Set<LivraisonEntity> livraisons;


    @ManyToMany
    private Set<EmployeEntity> employeEntitySet;

    /* @OneToOne
    private CamionEntity;*/

}
//test de performarmance pour les methodes