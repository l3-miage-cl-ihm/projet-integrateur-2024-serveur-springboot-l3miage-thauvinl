package fr.uga.l3miage.integrator.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Journee")
public class JourneeEntity {
    @Id
    private String reference;

    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy ="journee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourneeEntity> tournees = new HashSet<>();

    public void addTournee(TourneeEntity tournee){
        this.tournees.add(tournee);
        tournee.setJournee(this);
        tournee.setLettre(String.valueOf((char) ('A'+ (this.tournees.size()-1))));
        tournee.setReference(this.reference.replaceFirst("^j", "t")+ "-" + tournee.getLettre());
    }

}


