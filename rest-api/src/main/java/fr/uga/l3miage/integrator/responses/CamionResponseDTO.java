package fr.uga.l3miage.integrator.responses;

//import fr.uga.l3miage.integrator.dataType.GeoPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Représente un camion")
public class CamionResponseDTO {
    @Schema(description = "Immatriculation du camion")
    private String immatriculation;

}
