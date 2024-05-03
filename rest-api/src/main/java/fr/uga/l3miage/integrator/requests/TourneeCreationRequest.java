package fr.uga.l3miage.integrator.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TourneeCreationRequest {
    @Schema(description = "Description de l'état de la tournée, dépend de l'état des livraisons")
    private final String etatsDeTournee;
    @Schema(description = "Lettre associée à la tourné pour rendre sa référence unique")
    private final String lettre;
    @Schema(description = "Temps de montage total calculé à partir de toutes les livraisons de la tournée")
    private final Integer tempsDeMontageEffectif;
    @Schema(description = "Distance entre la dernière livraison de la tournée et l'entrepôt")
    private final Double distanceDeRetour;
    @Schema(description = "Ensemble des ids (trigramme) des employés qui livrent la tournée")
    private final Set<String> employesIds;
    @Schema(description = "Ensemble des livraisons de la tournée")
    private final Set<LivraisonCreationRequest> livraisons;
    @Schema(description = "immatriculation de camion")
    private final String refCamion;
}
