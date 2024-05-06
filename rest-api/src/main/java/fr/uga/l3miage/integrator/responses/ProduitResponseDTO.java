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
public class ProduitResponseDTO {
    @Schema(description = "Référence du produit" , example = "p01")
    private String reference;
    @Schema(description = "titre du produit" , example = "chaise turbis")
    private String titre;
    @Schema(description = "description du produit " , example = "La chaise turbis en plastique noir combine style et fonctionnalité.....")
    private String description;
    @Schema(description = "prix de produit" , example = "89.2")
    private Double prix;
    @Schema(description = "option de montage" , example = "true")
    private Boolean optionDeMontage;
    @Schema(description = "temps theorique de montage" , example = "50")
    private Integer tempsDeMontageTheorique;
}

