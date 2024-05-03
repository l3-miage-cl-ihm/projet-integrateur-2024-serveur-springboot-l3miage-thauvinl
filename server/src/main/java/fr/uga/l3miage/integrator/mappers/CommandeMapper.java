package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
@DecoratedWith(CommandeMapperDecorator.class)
public interface CommandeMapper {

    CommandeResponseDTO toResponseDTO(CommandeEntity entity);
}
