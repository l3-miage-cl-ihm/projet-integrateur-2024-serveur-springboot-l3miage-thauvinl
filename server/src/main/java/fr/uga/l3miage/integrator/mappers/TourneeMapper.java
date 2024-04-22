package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TourneeMapper {
    TourneeResponseDTO toResponse(TourneeEntity tourneeEntity);

    TourneeEntity toEntity(TourneeCreationRequest request);
}
