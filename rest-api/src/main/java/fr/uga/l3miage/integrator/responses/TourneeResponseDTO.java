package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TourneeResponseDTO {
    @Schema(description = "Référence de la tournée", example = "t001G-A")
    private String reference;
    @Schema(description = "Etat de la tournée (planifiee, enChargement, enParcours, enDechargement, enClientele, enMontage, enRetour, effectuee", example = "planifiee")
    private String etatsDeTournee;
    @Schema(description = "Lettre qui complète la référence de la tournée", example = "A")
    private String lettre;
    @Schema(description = "Montant total des livraisons d'une tournée en euros", example = "1568.2")
    private Double montant;
    @Schema(description = "Temps de montage théorique total de la tournée en minutes", example = "70")
    private Integer tempsDeMontageTheorique;
    @Schema(description = "Temps de montage effectif réalisé au cours de la tournée en minutes", example = "80")
    private Integer tempsDeMontageEffectif;
    @Schema(description = "Distance à parcourir au long de la tournée en km", example = "200.6")
    private Double distanceAParcourir;
    @Schema(description = "Distance entre la dernière livraison et l'entrepôt en km", example = "87.6")
    private Double distanceDeRetour;
    @Schema(description = "Liste des employés effectuant une tournée")
    private Set<EmployeResponseDTO> employeResponseDTOS;
    @Schema(description = "Liste des employés effectuant une tournée")
    private Set<LivraisonResponseDTO> livraisonResponseDTOS;
    @Schema(description = "Le camion qui est affecté à la tournée")
    private CamionResponseDTO camionResponseDTO;


}
