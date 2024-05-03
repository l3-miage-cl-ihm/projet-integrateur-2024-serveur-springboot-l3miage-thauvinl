package fr.uga.l3miage.integrator.models;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;

import fr.uga.l3miage.integrator.models.enums.EtatDeCommandeClass.EtatDeCommande;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandeEntity {
    @Id 
    private String reference;

    @Enumerated(EnumType.STRING)
    private EtatDeCommande etat;

    private LocalDateTime dateDeCreation;

    private Integer note;
    private String commentaire;

    private Double montant;
    private Integer tddTheorique;

    private Integer tdmTheorique;
    private LocalDateTime dateDeLivraisonEffective;

   private Integer dureeDeLivraison;

    @ManyToOne()
    private LivraisonEntity livraison;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LigneEntity> lignesCommandes;


}
