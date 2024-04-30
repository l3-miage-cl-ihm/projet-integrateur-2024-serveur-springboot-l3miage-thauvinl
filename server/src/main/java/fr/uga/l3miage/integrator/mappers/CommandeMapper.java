package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.mappers.CommandeMapperDecorator;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper  //(uses = LivraisonMapper.class)
@DecoratedWith(CommandeMapperDecorator.class)
public interface CommandeMapper {

    @Mapping(target = "livraison", source = "livraisonEntity")
    CommandeResponseDTO toCommandeResponseDTO(CommandeEntity entity);

    Set<CommandeResponseDTO> toCommandeResponseDTOList(Set<CommandeEntity> entities);
}
