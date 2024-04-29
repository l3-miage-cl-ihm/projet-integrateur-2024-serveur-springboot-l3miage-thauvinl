package fr.uga.l3miage.integrator.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class JourneeCreationRequest {
    private final Date date;
    private final Set<TourneeCreationRequest> tournees;

}
