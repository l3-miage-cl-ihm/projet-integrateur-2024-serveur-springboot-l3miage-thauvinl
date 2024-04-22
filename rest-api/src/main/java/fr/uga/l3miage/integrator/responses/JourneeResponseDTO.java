package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@Schema(description = "Représente une journée")
public class JourneeResponseDTO {
    @Schema(description = "Référence de la journée")
    private String reference;
    @Schema(description = "Date de la journée")
    private Date date;
    @Schema(description = "Distance à parcourir pendant la journée")
    private Double distanceAParcourir;
    @Schema(description = "Montant total des tournées de la journée")
    private Double montant;
    @Schema(description = "Temps de montage théorique total de la journée")
    private Integer tempsDeMontageTheorique;
    @Schema(description = "La liste des tournées de la journée")
    private Set<TourneeResponseDTO> tourneeResponseDTOS;
}
