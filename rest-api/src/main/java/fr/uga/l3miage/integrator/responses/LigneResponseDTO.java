package fr.uga.l3miage.integrator.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LigneResponseDTO {
    private String reference;
    private ProduitResponseDTO produit;
    private int quantite;
    private double montant;
    private boolean optionDeMontage;
}
