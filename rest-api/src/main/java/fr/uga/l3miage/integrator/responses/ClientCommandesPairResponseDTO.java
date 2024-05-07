package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Représente une pair d'adresse et liste de commandes")
public class ClientCommandesPairResponseDTO {
    private final AdresseResponseDTO adresse;
    private final Set<CommandeResponseDTO> commandes;
}
