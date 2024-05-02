package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper  //(uses = LivraisonMapper.class)
//@DecoratedWith(CommandeMapperDecorator.class)
public interface CommandeMapper {

    @Mapping(target = "livraison", source = "livraison")
    CommandeResponseDTO toResponseDTO(CommandeEntity entity);

    //Set<CommandeResponseDTO> toCommandeResponseDTOList(Set<CommandeEntity> entities);


}
