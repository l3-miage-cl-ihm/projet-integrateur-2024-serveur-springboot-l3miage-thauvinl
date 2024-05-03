package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface CommandeMapper {

    @Mapping(target = "reference", source = "reference")
    CommandeResponseDTO toResponse(CommandeEntity entity);




}
