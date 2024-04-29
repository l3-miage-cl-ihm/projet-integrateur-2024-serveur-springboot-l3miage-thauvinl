package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(TourneeMapperDecorator.class)
public interface TourneeMapper {
    @Mapping(target = "employeResponseDTOS", source = "employeEntitySet")
    TourneeResponseDTO toResponseWithEmployes(TourneeEntity tourneeEntity);
    @Mapping(target = "employeEntitySet", ignore = true)
    @Mapping(target = "reference",ignore = true)
    TourneeEntity toEntity(TourneeCreationRequest request);

    TourneeEntity toEntityWithJourneeRef(TourneeCreationRequest tournee, String reference);
}
