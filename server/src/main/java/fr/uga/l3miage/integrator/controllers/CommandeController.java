package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.CommandeEndpoint;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/commandes")
public class CommandeController implements CommandeEndpoint {

    @Autowired
    private CommandeService commandeService;

    @Override
    public Set<CommandeResponseDTO> getAllCommandes() {
        Set<CommandeResponseDTO> commandes = commandeService.getAllCommandes();
        return commandes;
    }

    @GetMapping("/{reference}")
    public CommandeResponseDTO getCommandeByReference(@PathVariable String reference) {
        CommandeResponseDTO commande = commandeService.getCommandeByReference(reference);
        return commande;
    }

    @Override
    public Map<String,Set<CommandeResponseDTO>> getCommandesGroupedByClient() {
        return commandeService.getCommandesGroupedByClient();
    }


}
