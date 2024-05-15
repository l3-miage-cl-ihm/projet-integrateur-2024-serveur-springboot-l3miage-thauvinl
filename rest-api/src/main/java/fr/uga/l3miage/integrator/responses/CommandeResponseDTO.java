package fr.uga.l3miage.integrator.responses;


import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Schema(description = "Représente une commande")
public class CommandeResponseDTO {
    @Schema(description = "Référence de la commande",example = "c001")
    private String reference;

    @Schema(description = "État de la commande",example = "ouverte")
    private String etat;

    @Schema(description = "Date de création de la commande",example = "2024-05-04T13:00:09")
    private LocalDateTime dateDeCreation;

    @Schema(description = "Note attribuée à la commande",example = "4")
    private Integer note;

    @Schema(description = "Commentaire associé à la commande")
    private String commentaire;

    @Schema(description = "Montant de la commande",example = "349.00")
    private Double montant;

    @Schema(description = "Temps de montage théorique",example = "34")
    private Integer tdmTheorique;

    @Schema(description = "Date de livraison effective de la commande",example = "2024-06-04T13:00:09")
    private LocalDateTime dateDeLivraisonEffective;

    @Schema(description = "Durée de livraison de la commande",example = "12")
    private Integer dureeDeLivraison;

    private Set<LigneResponseDTO> lignes;


}
