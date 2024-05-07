package fr.uga.l3miage.integrator.controllers;
import fr.uga.l3miage.integrator.endpoints.ClientEndpoints;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
import fr.uga.l3miage.integrator.requests.ClientCreationRequest;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import fr.uga.l3miage.integrator.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientEndpoints {

    private final ClientService clientService;

/*
    @Override
    public ClientResponseDTO createClient(@Valid @RequestBody ClientCreationRequest clientCreationRequest) {
        return clientService.createClient(clientCreationRequest);
    }

 */

    @Override
    public ClientResponseDTO getClientByEmail(String email) {
        return clientService.getClientByEmail(email);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        return clientService.getAllClients();
    }
}