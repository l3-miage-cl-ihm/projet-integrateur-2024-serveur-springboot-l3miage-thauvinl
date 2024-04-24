package fr.uga.l3miage.integrator.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TourneeCreationRequest {
    private final String reference;
    private final String etatsDeTournee;
    private final String lettre;
    private final Double montant;
    private final Integer tempsDeMontageTheorique;
    private final Integer tempsDeMontageEffectif;
    private final Double distanceAParcourir;
    private final Double distanceDeRetour;
    private final String refJournee;
    private final Set<EmployeCreationRequest> employes;
}
