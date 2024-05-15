package fr.uga.l3miage.integrator.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LigneResponseDTO {
    @Schema(description = "Référence de la ligne" , example = "l01")
    private String reference;
    @Schema(description = "Rle produit" )
    private ProduitResponseDTO produit;
    @Schema(description = "quantité du produit" , example = "11")
    private int quantite;
    @Schema(description = "prix " , example = "30")
    private double montant;
    @Schema(description = "est-ce que le client a choisit l'option montage ou non" , example = "true")
    private boolean optionDeMontage;
}
