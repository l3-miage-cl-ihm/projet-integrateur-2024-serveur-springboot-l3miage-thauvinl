package fr.uga.l3miage.integrator.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TourneeCreationRequest {
    private final String etatsDeTournee;
    private final String lettre;
    private final Integer tempsDeMontageEffectif;
    private final Double distanceDeRetour;
    private final String refJournee;
    private final Set<EmployeCreationRequest> employes;
}
