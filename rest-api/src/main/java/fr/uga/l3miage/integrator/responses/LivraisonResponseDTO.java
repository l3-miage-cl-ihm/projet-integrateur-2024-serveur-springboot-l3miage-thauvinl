package fr.uga.l3miage.integrator.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Schema(description = "Représente une livraison")
public class LivraisonResponseDTO {
    @Schema(description = "Référence de la livraison")
    private String reference;

    @Schema(description = "État de la livraison")
    private String etat;

    @Schema(description = "Montant de la livraison")
    private Double montant;

    @Schema(description = "Distance à parcourir")
    private Double distanceAParcourir;

    @Schema(description = "Temps de trajet à l'aller")
    private Integer tdtALAller;

    @Schema(description = "Temps de déchargement théorique")
    private Integer tddTheorique;

    @Schema(description = "Temps de montage théorique")
    private Integer tdmTheorique;

    @Schema(description = "Heure de livraison théorique")

    private LocalDateTime heureLivraison;
/*
    @Schema(description = "Heure de livraison effective")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
    private Time heureDeLivraisonEffective;*/

    @Schema(description = "Temps de montage effectif")
    private Integer tdmEffectif;

    @Schema(description = "les commandes dans la livraison")
    private Set<CommandeResponseDTO> commandes;




}
