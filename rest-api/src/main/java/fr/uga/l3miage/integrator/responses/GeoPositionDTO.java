package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoPositionDTO {
    @Schema(description = "La latitude a la quelle ce trouve le comion", example = "0")
    private Double latitude;
    @Schema(description = "La longitude a la quelle ce trouve le camion", example = "0")
    private Double longitude;
}
