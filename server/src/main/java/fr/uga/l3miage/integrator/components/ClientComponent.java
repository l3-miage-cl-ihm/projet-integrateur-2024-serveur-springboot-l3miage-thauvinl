package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ClientComponent {
    private final ClientRepository clientRepository;

    public ClientEntity getClientByEmail(String email) {
        return clientRepository.findClientEntityByEmail(email);
    }

}