package fr.uga.l3miage.integrator.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TourneeCreationRequest {
    @Schema(description = "Description de l'état de la tournée, dépend de l'état des livraisons", example = "planifiee")
    private final String etatsDeTournee;
    @Schema(description = "Lettre associée à la tourné pour rendre sa référence unique", example = "A")
    private final String lettre;
    @Schema(description = "Temps de montage total calculé à partir de toutes les livraisons de la tournée", example = "82")
    private final Integer tempsDeMontageEffectif;
    @Schema(description = "Distance entre la dernière livraison de la tournée et l'entrepôt", example = "103")
    private final Double distanceDeRetour;
    @Schema(description = "Ensemble des ids (trigramme) des employés qui livrent la tournée", example = "[AAA]")
    private final Set<String> employesIds;
    @Schema(description = "Ensemble des livraisons de la tournée", example = "[l001G-A1]")
    private final Set<LivraisonCreationRequest> livraisons;
    @Schema(description = "immatriculation de camion", example = "AB-123-CD")
    private final String refCamion;
}
