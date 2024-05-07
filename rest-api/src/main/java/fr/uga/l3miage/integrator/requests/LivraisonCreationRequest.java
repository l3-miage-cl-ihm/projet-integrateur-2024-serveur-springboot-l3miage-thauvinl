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

        @Schema(description = "L'état de la livraison", example = "en cours")
        private final String etat;

        @Schema(description = "Le montant de la livraison", example = "50.0")
        private float montant;

        @Schema(description = "La distance parcourue lors de la livraison", example = "120.5")
        private float distanceParcourue;

        @Schema(description = "Le temps de trajet aller-retour (en minutes)", example = "30")
        private Integer tdtALAller;

        @Schema(description = "Le temps de préparation théorique (en minutes)", example = "60")
        private Integer tdpTheorique;

        @Schema(description = "Le temps de déplacement théorique (en minutes)", example = "45")
        private Integer tddTheorique;

        @Schema(description = "Le temps de montage théorique (en minutes)", example = "120")
        private Integer tdmTheorique;

        @Schema(description = "L'heure de livraison théorique", example = "10:00:00")
        private Time heureDeLivraisonTheorique;

        @Schema(description = "L'heure de livraison effective", example = "10:30:00")
        private Time heureDeLivraisonEffective;

        @Schema(description = "Le temps de montage effectif (en minutes)", example = "150")
        private Integer tdmEffectif;

        @Schema(description = "Les références des commandes associées à la livraison")
        private final Set<String> refCommande;

}
