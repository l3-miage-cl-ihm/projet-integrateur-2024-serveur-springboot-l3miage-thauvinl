package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ClientComponent {

    private final ClientRepository clientRepository;

    public ClientEntity getClientByEmail(String email) throws NotFoundClientEntityExeption {
        ClientEntity client = clientRepository.findClientEntityByEmail(email).orElseThrow(()-> new NotFoundClientEntityExeption(String.format("Le client dont l' email est %s est introuvable",email)));
        return client;
    }

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }



}
