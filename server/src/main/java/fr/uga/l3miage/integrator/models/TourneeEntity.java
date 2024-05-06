package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.models.enums.EtatDeTournee;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tournee")
public class TourneeEntity {
    @Id
    private String reference;

    @Enumerated(EnumType.STRING)
    private EtatDeTournee etatsDeTournee;


    private String lettre;
    private Integer tempsDeMontageEffectif;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
    private Double distanceDeRetour;

    @OneToMany(mappedBy = "tournee", cascade = CascadeType.ALL)
    private Set<LivraisonEntity> livraisons = new HashSet<>();

    @ManyToOne
    private  JourneeEntity journee;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<EmployeEntity> employeEntitySet = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private CamionEntity camion;

    public void addLivraison(LivraisonEntity livraison){
        this.livraisons.add(livraison);
        livraison.setTournee(this);
        livraison.setReference(this.reference.replaceFirst("^t", "l")+ (livraisons.size()));
    }
}
