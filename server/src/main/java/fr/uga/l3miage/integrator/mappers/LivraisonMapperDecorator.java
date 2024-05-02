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
        LivraisonEntity livraisonEntity = livraisonMapper.toEntity(request);
        System.out.println("ok i guesss ");
        return livraisonEntity;
    }

    @Override
    public LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity) {
        System.out.println("ok i guesss 2 ");
        LivraisonResponseDTO responseDTO =  livraisonMapper.toResponse(livraisonEntity);
        responseDTO.setMontant(10);
        responseDTO.setTdmTheorique(15);
        responseDTO.setDistanceParcourue(100);
        return responseDTO;
    }
}
