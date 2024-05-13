package fr.uga.l3miage.integrator.controllers;
import fr.uga.l3miage.integrator.endpoints.JourneeEndpoints;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.services.JourneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JourneeController implements JourneeEndpoints {

    private final JourneeService journeeService;
    @Override
    public JourneeResponseDTO getJournee (String reference) {
        return journeeService.getJournee(reference);
    }

    @Override
    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest){
        return journeeService.createJournee(journeeCreationRequest);
    }
}
