package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TourneeMapper.class)
@DecoratedWith(JourneeMapperDecorator.class)
public interface JourneeMapper {

    JourneeResponseDTO toResponseWithTournees(JourneeEntity journeeEntity);

    @Mapping(target = "tournees", ignore = true)
    @Mapping(target = "reference", ignore = true)
    JourneeEntity toEntity (JourneeCreationRequest request);

}
