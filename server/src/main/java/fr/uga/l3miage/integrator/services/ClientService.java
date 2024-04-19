package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.ClientComponent;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

   /* Version sans component
   private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<ClientEntity> getClientByEmail(String email) {
        return clientRepository.findById(email);
    }*/
    public final ClientComponent clientComponent;
    public ClientEntity getClientByEmail(String email){
        return clientComponent.getClientByEmail(email);
    }

}
