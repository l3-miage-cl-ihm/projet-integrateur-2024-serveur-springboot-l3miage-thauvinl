package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@Schema(description = "Respresente un employe")
public class EmployeResponseDTO {
    @Schema(description = "Trigramme de l'employé")
    private String trigramme;
    @Schema(description = "Email de l'employé")
    private String email;
    @Schema(description = "Prénom de l'employé")
    private String prenom;
    @Schema(description = "Nom de l'employé")
    private String nom;
    @Schema(description = "Téléphone de l'employé")
    private String telephone;
    @Schema(description = "Emploi de l'employe")
    private String emploi;
}
