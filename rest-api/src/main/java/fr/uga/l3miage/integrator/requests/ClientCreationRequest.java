package fr.uga.l3miage.integrator.requests;


import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ClientCreationRequest {

    private String email;


    private String prenom;


    private String nom;


    private String etat;


    private Float montantTotal;

    private AdresseResponseDTO adresse;
}
