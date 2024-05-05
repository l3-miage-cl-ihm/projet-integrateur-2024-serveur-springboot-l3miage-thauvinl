package fr.uga.l3miage.integrator.requests;


import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import lombok.Builder;
import lombok.Data;


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
