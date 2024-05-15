package fr.uga.l3miage.integrator.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Set;

@Data
@Builder
public class LivraisonCreationRequest {

        @Schema(description = "La référence de la livraison", example = "L001")
        private final String reference;

        @Schema(description = "La distance parcourue lors de la livraison", example = "120.5")
        private Double distanceAParcourir;

        @Schema(description = "Le temps de trajet aller (en minutes)", example = "30")
        private Integer tdtALAller;

        @Schema(description = "Le temps de déchargement théorique (en minutes)", example = "45")
        private Integer tddTheorique;

        @Schema(description = "L'heure de livraison théorique", example = "10:00:00")
        private Time heureDeLivraisonTheorique;

        @Schema(description = "Les références des commandes associées à la livraison")
        private final Set<String> refCommande;

}
