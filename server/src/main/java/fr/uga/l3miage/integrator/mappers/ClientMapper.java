package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.requests.ClientCreationRequest;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {

    //@Mapping(target = "clientResponseDTOS", source = "client")
    ClientResponseDTO toResponse(ClientEntity client);

    //@Mapping(target = "client", ignore = true)
    ClientEntity toEntity (ClientCreationRequest request);
}

