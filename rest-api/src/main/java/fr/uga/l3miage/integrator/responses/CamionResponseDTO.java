package fr.uga.l3miage.integrator.responses;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Représente un camion")
public class CamionResponseDTO {
    @Schema(description = "Immatriculation du camion", example = "AB-123-CD")
    private String immatriculation;
    @Schema(description = "geo position du camion", example = "0,0")
    private final GeoPositionDTO geoPositionDTO;
}
