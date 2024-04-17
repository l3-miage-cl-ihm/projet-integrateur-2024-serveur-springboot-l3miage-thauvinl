package fr.uga.l3miage.integrator.controllers;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.services.JourneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/journees")
public class JourneeController {

    private final JourneeService journeeService;

    @Autowired
    public JourneeController(JourneeService journeeService) {
        this.journeeService = journeeService;
    }

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
    @PostMapping
    public ResponseEntity<JourneeEntity> createJournee(@RequestBody JourneeEntity journee) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse("15/04/2024");
            journee.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
        JourneeEntity savedJournee = journeeService.createJournee(journee);
        return new ResponseEntity<>(savedJournee, HttpStatus.CREATED);
    }
}
