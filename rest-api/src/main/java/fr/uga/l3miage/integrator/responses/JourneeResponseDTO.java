package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@Schema(description = "Représente une journée")
public class JourneeResponseDTO {
    @Schema(description = "Référence de la journée" , example = "j001G")
    private String reference;
    @Schema(description = "Date de la journée", example = "2024-04-01T00:00:00.000+00:00")
    private Date date;
    @Schema(description = "Distance à parcourir pendant la journée", example = "200.6")
    private Double distanceAParcourir;
    @Schema(description = "Montant total des tournées de la journée", example = "1568.2")
    private Double montant;
    @Schema(description = "Temps de montage théorique total de la journée", example = "70")
    private Integer tempsDeMontageTheorique;
    @Schema(description = "La liste des tournées de la journée")
    private Set<TourneeResponseDTO> tourneeResponseDTOS;
}
