package fr.uga.l3miage.integrator.controllers;
import fr.uga.l3miage.integrator.endpoints.JourneeEndpoints;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.services.JourneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JourneeController implements JourneeEndpoints {

    private final JourneeService journeeService;

    @Override
    public JourneeResponseDTO addTourneeInJournee(String journeeReference, String tourneeReference){
        return journeeService.addTourneeInJournee(journeeReference, tourneeReference);
    }

    @Override
    public JourneeResponseDTO getJournee (String reference) {
        return journeeService.getJournee(reference);
    }

    @Override
    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest) {
        return journeeService.createJournee(journeeCreationRequest);
    }

}
