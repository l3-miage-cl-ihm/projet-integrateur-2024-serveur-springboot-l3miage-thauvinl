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



    @GetMapping("/{reference}")
    public ResponseEntity<JourneeEntity> getJournee(@PathVariable String reference) {
        return journeeService.findJourneeByReference(reference)
                .map(journee -> ResponseEntity.ok(journee)) // Si la journée est trouvée, retourne la journée avec un statut 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si la journée n'est pas trouvée, retourne un statut 404 Not Found
    }
    @GetMapping
    public ResponseEntity<List<JourneeEntity>> getAllJournees() {
        List<JourneeEntity> journees = journeeService.findAllJournees();
        return ResponseEntity.ok(journees);
    }
    @Override
    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest) {
        return journeeService.createJournee(journeeCreationRequest);
    }

    @GetMapping("/{reference}/tournees")
    public ResponseEntity<List<TourneeEntity>> getAllTourneesOfJournee(@PathVariable String reference) {
        List<TourneeEntity> tournees = journeeService.getAllTourneesOfJournee(reference);
        return ResponseEntity.ok().body(tournees);
    }

    /*@PatchMapping("/{reference}")
    public ResponseEntity<JourneeEntity> updateJournee(@PathVariable String reference, @RequestBody JourneeEntity journee){
        JourneeEntity updatedJournee = journeeService.updateJournee(reference, journee);
        if (updatedJournee != null) {
            return ResponseEntity.ok().body(updatedJournee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
