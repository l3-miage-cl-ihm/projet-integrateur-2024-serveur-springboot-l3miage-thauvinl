package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourneeResponseDTO {
    @Schema(description = "Référence de la tournée")
    private String reference;
    @Schema(description = "Etat de la tournée (planifiee, enChargement, enParcours, enDechargement, enClientele, enMontage, enRetour, effectuee")
    private String etatsDeTournee;
    @Schema(description = "Lettre qui complète la référence de la tournée")
    private String lettre;
    @Schema(description = "Montant total des livraisons d'une tournée")
    private Double montant;
    @Schema(description = "Temps de montage théorique total de la tournée")
    private Integer tempsDeMontageTheorique;
    @Schema(description = "Temps de montage effectif réalisé au cours de la tournée")
    private Integer tempsDeMontageEffectif;
    @Schema(description = "Distance à parcourir au long de la tournée")
    private Double distanceAParcourir;
    @Schema(description = "Distance entre la dernière livraison et l'entrepôt")
    private Double distanceDeRetour;
}
