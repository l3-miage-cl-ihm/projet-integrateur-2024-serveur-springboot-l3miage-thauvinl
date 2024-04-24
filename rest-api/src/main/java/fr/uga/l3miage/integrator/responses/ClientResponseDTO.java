package fr.uga.l3miage.integrator.responses;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponseDTO {
    @Schema(description = "Adresse email du client")
    private String email;

    @Schema(description = "Prénom du client")
    private String prenom;

    @Schema(description = "Nom du client")
    private String nom;

    @Schema(description = "État du client")
    private String etat;

    @Schema(description = "Montant total des commandes du client")
    private Float montantTotal;

    @Schema(description = "Adresse du client")
    private AdresseResponseDTO adresse;

}
