package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class LivraisonMapperDecorator implements LivraisonMapper{
    @Autowired
    @Qualifier("delegate")
    private LivraisonMapper livraisonMapper;


    @Override
    public LivraisonEntity toEntity(LivraisonCreationRequest request) {
        return null;
    }

    @Override
    public LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity) {
        return null;
    }
}
