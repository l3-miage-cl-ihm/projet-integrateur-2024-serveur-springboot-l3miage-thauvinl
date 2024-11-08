package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;

import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = CommandeMapper.class)
@DecoratedWith(LivraisonMapperDecorator.class)
public interface LivraisonMapper {
    @Mapping(target = "commandes", ignore = true)
    LivraisonEntity toEntity(LivraisonCreationRequest request);

    LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity);


}
