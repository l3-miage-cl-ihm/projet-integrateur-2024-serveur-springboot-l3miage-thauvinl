package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.models.enums.Emploi;

import javax.persistence.Entity;
import javax.persistence.*;
import java.awt.*;

@Entity
public class EmployeEntity {
    @Id
    private String trigramme;
    private String email;
    private String prenom;
    private String nom;
    //@Embedded
    //private Image photo;
    private String telephone;
    private Emploi emploi;

}
