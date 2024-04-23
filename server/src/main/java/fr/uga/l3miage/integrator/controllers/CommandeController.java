package fr.uga.l3miage.integrator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping("/AllCommandes")
    public ResponseEntity<List<CommandeEntity>> getAllCommandes() {
        List<CommandeEntity> commandes = commandeRepository.findAll();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<CommandeEntity> getCommandeByReference(@PathVariable String reference) {
        CommandeEntity commande = commandeRepository.findById(reference).orElse(null);
        if (commande == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commande, HttpStatus.OK);
    }


}
