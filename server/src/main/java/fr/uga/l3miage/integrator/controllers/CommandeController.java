package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.CommandeEndpoint;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequiredArgsConstructor
public class CommandeController implements CommandeEndpoint {


    private final CommandeService commandeService;

    @Override
    public Set<CommandeResponseDTO> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

   @Override
    public CommandeResponseDTO getCommandeByReference(String reference) {
        return commandeService.getCommandeByReference(reference);
    }

    @Override
    public Map<AdresseResponseDTO,Set<CommandeResponseDTO>> getCommandesGroupedByClient() {
        return commandeService.getCommandesGroupedByClient();
    }


}
