package fr.uga.l3miage.integrator.dataType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Adresse {
    private String adresse;
    private String codePostal;
    private String ville;


}
