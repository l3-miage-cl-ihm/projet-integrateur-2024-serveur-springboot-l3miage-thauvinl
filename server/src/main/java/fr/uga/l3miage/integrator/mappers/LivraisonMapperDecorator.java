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
        System.out.println("ok i guesss ");
        return livraisonMapper.toEntity(request);
    }

    @Override
    public LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity) {
        System.out.println("ok i guesss 2 ");
        return livraisonMapper.toResponse(livraisonEntity);
    }
}
