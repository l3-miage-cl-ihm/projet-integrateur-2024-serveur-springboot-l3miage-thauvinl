package fr.uga.l3miage.integrator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitEntity {
    @Id
    private String reference;
    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double prix;
    private Boolean optionDeMontage;
    private Integer tempsDeMontageTheorique;
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LigneEntity> lignesProduits;

}
