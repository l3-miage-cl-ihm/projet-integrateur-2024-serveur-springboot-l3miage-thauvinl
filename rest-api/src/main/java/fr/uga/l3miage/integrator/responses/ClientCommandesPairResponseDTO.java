package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Représente une pair d'adresse et liste de commandes")
public class ClientCommandesPairResponseDTO {
    @Schema(description = "adresse Client",example = "adresse: 13 rue centrale, ville:Grenoble, codePostal:38000")
    private final AdresseResponseDTO adresse;
    @Schema(description = "Set de Commandes")
    private final Set<CommandeResponseDTO> commandes;
}
