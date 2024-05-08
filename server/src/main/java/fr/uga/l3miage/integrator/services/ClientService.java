package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.ClientComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
import fr.uga.l3miage.integrator.mappers.ClientMapper;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    public final ClientComponent clientComponent;

    public ClientResponseDTO getClientByEmail(String email) {
        try{
            return clientMapper.toResponse(clientComponent.getClientByEmail(email));
        } catch (NotFoundClientEntityExeption c){
            throw new NotFoundEntityRestException(c.getMessage());
        }
    }

    public List<ClientResponseDTO> getAllClients() {
        List<ClientEntity> clients = clientComponent.getAllClients();
        return clients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }



}
