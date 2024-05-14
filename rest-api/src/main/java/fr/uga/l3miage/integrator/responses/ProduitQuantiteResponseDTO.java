package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Représente une liste de produit et leur quantite")
@AllArgsConstructor
public class ProduitQuantiteResponseDTO {
    @Schema(description = "un produit" )
    private final ProduitResponseDTO produit;
    @Schema(description = "quantité du produit" , example = "2")
    private final Integer quantite;
}
