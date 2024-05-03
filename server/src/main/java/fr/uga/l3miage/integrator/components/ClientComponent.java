package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ClientComponent {
    private final ClientRepository clientRepository;

    public ClientEntity getClientByEmail(String email) {
        return clientRepository.findClientEntityByEmail(email);
    }

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll(); // Suppose que votre repository possède une méthode findAll pour récupérer tous les clients.
    }


    public ClientEntity createClient(ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

}
