package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.models.enums.Emploi;

import javax.persistence.Entity;
import javax.persistence.*;

import lombok.*;

import java.awt.*;
import java.util.Set;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeEntity {
    @Id
    private String trigramme;
    @Column(nullable = false, unique = true)
    private String email;
    private String prenom;
    private String nom;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Emploi emploi;

    @ManyToMany(mappedBy = "employeEntitySet")
    private Set<TourneeEntity> tourneeEntitySet;
}
