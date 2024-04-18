package fr.uga.l3miage.integrator.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @OneToMany
    @JoinColumn(name="journee_entity_reference", referencedColumnName = "reference")
    private List<TourneeEntity> tournees;
}
