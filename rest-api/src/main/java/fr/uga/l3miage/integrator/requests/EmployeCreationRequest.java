package fr.uga.l3miage.integrator.requests;

//import fr.uga.l3miage.integrator.models.enums.Emploi;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeCreationRequest {
    private final String trigramme;
    private final String email;
    private final String prenom;
    private final String nom;
    private final String telephone;
    private final String emploi;
}