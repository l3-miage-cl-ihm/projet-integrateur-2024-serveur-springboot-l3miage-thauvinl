package fr.uga.l3miage.integrator.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;



import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@Schema(description = "Représente une commande")
public class CommandeResponseDTO {
    @Schema(description = "Référence de la commande")
    private String reference;

    //@Schema(description = "État de la commande")
    //private EtatDeCommande etat;

    @Schema(description = "Date de création de la commande")
    private Date dateDeCreation;

    @Schema(description = "Note attribuée à la commande")
    private Integer note;

    @Schema(description = "Commentaire associé à la commande")
    private String commentaire;

    @Schema(description = "Montant de la commande")
    private float montant;

    @Schema(description = "Temps de déplacement théorique")
    private Integer tddTheorique;

    @Schema(description = "Temps de montage théorique")
    private Integer tdmTheorique;

    @Schema(description = "Date de livraison effective de la commande")
    private Date dateDeLivraisonEffective;

    @Schema(description = "Durée de livraison de la commande")
    private Integer dureeDeLivraison;

    @Schema(description = "Livraison associée à la commande")
    private LivraisonResponseDTO livraison;
}
