package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.models.enums.Emploi;

import javax.persistence.Entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.*;
import java.util.Set;
@Entity
@Data
public class EmployeEntity {
    @Id
    private String trigramme;
    private String email;
    private String prenom;
    private String nom;
    //@Embedded private byte[] photo;


    private String telephone;

    @Enumerated(EnumType.STRING)
    private Emploi emploi;

    @ManyToMany(mappedBy = "employeEntitySet")
    private Set<TourneeEntity> tourneeEntitySet;
}
