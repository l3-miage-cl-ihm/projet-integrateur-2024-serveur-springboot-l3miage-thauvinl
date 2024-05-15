package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.LigneEntity;
import fr.uga.l3miage.integrator.responses.LigneResponseDTO;
import org.mapstruct.Mapper;

@Mapper(uses = ProduitMapper.class)
public interface LigneMapper {
    LigneResponseDTO toResponse(LigneEntity ligneEntity);
}
