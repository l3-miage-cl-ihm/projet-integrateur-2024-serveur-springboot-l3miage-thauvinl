package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@Schema(description = "Respresente un employe")
public class EmployeResponseDTO {
    @Schema(description = "Trigramme de l'employé", example = "CMJ")
    private String trigramme;
    @Schema(description = "Email de l'employé", example = "citeb.maruj@ikeo.com")
    private String email;
    @Schema(description = "Prénom de l'employé", example = "citeb")
    private String prenom;
    @Schema(description = "Nom de l'employé", example = "MARUJ")
    private String nom;
    @Schema(description = "numéro de téléphone de l'employé", example = "657477854")
    private String telephone;
    @Schema(description = "Emploi de l'employe", example = "livreur")
    private String emploi;
}
