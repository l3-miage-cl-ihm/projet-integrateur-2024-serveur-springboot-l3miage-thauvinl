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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<EmployeEntity> employeEntitySet = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private CamionEntity camion;

    public void addLivraison(LivraisonEntity livraison){
        this.livraisons.add(livraison);
        livraison.setTournee(this);
    }

    public void addEmploye(EmployeEntity employe){
        this.employeEntitySet.add(employe);
        employe.getTourneeEntitySet().add(this);
    }

    public void setTempsDeMontageEffectif(Integer tempsDeMontageEffectif) throws IllegalArgumentException{
        if (tempsDeMontageEffectif == null || tempsDeMontageEffectif < 0 || tempsDeMontageEffectif < this.tempsDeMontageEffectif) {
            throw new IllegalArgumentException("Le temps de montage effectif doit être un entier naturel.");
        }
        else{
            this.tempsDeMontageEffectif = tempsDeMontageEffectif;
        }
    }
}
