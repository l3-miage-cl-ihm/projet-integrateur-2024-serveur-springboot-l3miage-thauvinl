package fr.uga.l3miage.integrator.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class JourneeCreationRequest {
    private final String reference;
    private final Date date;
    private final Double distanceAParcourir;
    private final Double montant;
    private final Integer tempsDeMontageTheorique;
    private final Set<TourneeCreationRequest> tournees;

}
