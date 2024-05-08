package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Représente une adresse")
public class AdresseResponseDTO {
    @Schema(description = "Adresse")
    private String adresse;

    @Schema(description = "Code postal")
    private String codePostal;

    @Schema(description = "Ville")
    private String ville;

}
