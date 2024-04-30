package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;

import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Mapper
@DecoratedWith(LivraisonMapperDecorator.class)
public interface LivraisonMapper {
    //@Mapping(target = "commandes", ignore = true)
    //@Mapping(target = "reference",ignore = true)
    LivraisonEntity toEntity(LivraisonCreationRequest request);

    LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity);


}
