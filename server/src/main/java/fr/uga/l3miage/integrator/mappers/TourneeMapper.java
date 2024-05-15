package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = LivraisonMapper.class)
@DecoratedWith(TourneeMapperDecorator.class)
public interface TourneeMapper {

    @Mapping(target = "employeEntitySet", ignore = true)
    @Mapping(target = "livraisons", ignore = true)
    TourneeEntity toEntity(TourneeCreationRequest request);

    @Mapping(target = "livreurs", source = "employeEntitySet")
    @Mapping(target = "livraisons", source = "livraisons")
    @Mapping(target = "etat", source = "etatsDeTournee")
    TourneeResponseDTO toResponse(TourneeEntity tourneeEntity);

}
