package fr.uga.l3miage.integrator.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class JourneeCreationRequest {
    @Schema(description = "Date de la journée planifiée", example = "2024-04-01")
    private final Date date;
    @Schema(description = "Ensemble des tournées prévues sur la journée")
    private final Set<TourneeCreationRequest> tournees;

}
