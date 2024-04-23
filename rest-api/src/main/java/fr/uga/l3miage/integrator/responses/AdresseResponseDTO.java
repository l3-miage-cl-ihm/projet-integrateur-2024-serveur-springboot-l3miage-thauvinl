package fr.uga.l3miage.integrator.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    // Constructeurs, getters et setters peuvent être ajoutés selon les besoins
}
