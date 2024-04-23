package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JourneeMapper {
    @Mapping(target = "tourneeResponseDTOS", source = "tournees")
    JourneeResponseDTO toResponseWithTournees(JourneeEntity journeeEntity);

    @Mapping(target = "tournees", ignore = true)
    JourneeEntity toEntity(JourneeCreationRequest request);
}
