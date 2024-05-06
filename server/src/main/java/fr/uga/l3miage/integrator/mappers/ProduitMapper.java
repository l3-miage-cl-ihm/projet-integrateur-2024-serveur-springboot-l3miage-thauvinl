package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.responses.ProduitResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProduitMapper {

    ProduitResponseDTO toResponse(ProduitEntity produit);
}
