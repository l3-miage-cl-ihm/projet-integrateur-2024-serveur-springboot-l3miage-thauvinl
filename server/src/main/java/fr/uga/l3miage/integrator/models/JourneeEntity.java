package fr.uga.l3miage.integrator.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JourneeEntity {
    @Id
    private String reference;
    private Date date;
    private Double distanceAParcourir;
    private Double montant;
    private Integer tempsDeMontageTheorique;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="journee_reference")
    private Set<TourneeEntity> tournees;
}
