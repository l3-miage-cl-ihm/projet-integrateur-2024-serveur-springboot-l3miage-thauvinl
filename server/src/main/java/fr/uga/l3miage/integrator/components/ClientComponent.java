package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ClientComponent {

    private final ClientRepository clientRepository;

    public ClientEntity getClientByEmail(String email) throws NotFoundClientEntityException {
        return clientRepository.findClientEntityByEmail(email).orElseThrow(()-> new NotFoundClientEntityException(String.format("Le client dont l' email est %s est introuvable",email)));

    }

}
