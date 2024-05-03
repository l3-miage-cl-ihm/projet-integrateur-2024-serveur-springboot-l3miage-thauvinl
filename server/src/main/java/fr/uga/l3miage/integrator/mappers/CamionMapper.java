package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CamionMapper {
    @Mapping(source = "immatriculation", target = "immatriculation")
    CamionResponseDTO toResponse(CamionEntity camion);
}
