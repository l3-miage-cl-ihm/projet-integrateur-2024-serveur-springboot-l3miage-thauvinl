package fr.uga.l3miage.integrator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.awt.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitEntity {
    @Id
    private String Id;
    //@Embedded
    //private Image photo;
    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double prix;
    private Boolean optionDeMontage;
    private Integer tempsDeMontageTheorique;

}
