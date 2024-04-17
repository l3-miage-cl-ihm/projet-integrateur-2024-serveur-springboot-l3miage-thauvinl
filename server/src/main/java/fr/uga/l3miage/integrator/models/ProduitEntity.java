package fr.uga.l3miage.integrator.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProduitEntity {
    @Id
    private String Id;
    private String photo;
    private String titre;
    private String description;
    private Double prix;
    private Boolean optionDeMontage;
    private Integer tempsDeMontageTheorique;

}
