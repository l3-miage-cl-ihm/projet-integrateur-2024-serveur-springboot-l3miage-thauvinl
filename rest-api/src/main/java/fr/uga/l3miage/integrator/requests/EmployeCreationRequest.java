package fr.uga.l3miage.integrator.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeCreationRequest {
    @Schema(description = "")
    private final String trigramme;
    @Schema(description = "")
    private final String email;
    @Schema(description = "")
    private final String prenom;
    @Schema(description = "")
    private final String nom;
    @Schema(description = "")
    private final String telephone;
    @Schema(description = "")
    private final String emploi;
}