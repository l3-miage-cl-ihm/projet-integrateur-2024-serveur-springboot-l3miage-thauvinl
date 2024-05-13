package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.CommandeEndpoint;
import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public CommandeResponseDTO updateDateDeLivraison(String reference, String date){
        return commandeService.updateDateDeLivraison(reference, date);
    }

    @Override
    public Set<ClientCommandesPairResponseDTO> getCommandesGroupedByClient() {
        return commandeService.getCommandesGroupedByClient();
    }

    @Override
    public CommandeResponseDTO updateEtat(String reference, String nvEtat) {
        return commandeService.updateEtat(reference,nvEtat);
    }
    @Override
    public Set<CommandeResponseDTO> getAllCommandeByLivraison(String reference){
        return commandeService.getAllCommandeByLivraison(reference);
    }

}
