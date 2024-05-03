package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LivraisonMapperDecorator implements LivraisonMapper{
    @Autowired
    @Qualifier("delegate")
    private LivraisonMapper livraisonMapper;

    @Autowired
    private CommandeMapper commandeMapper;

    @Override
    public LivraisonEntity toEntity(LivraisonCreationRequest request) {
        LivraisonEntity livraisonEntity = livraisonMapper.toEntity(request);
        Set<CommandeEntity> cmd=new HashSet<>();
        livraisonEntity.setCommandes(cmd);
        return livraisonEntity;
    }

    @Override
    public LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity) {

        LivraisonResponseDTO responseDTO =  livraisonMapper.toResponse(livraisonEntity);
        System.out.println(livraisonEntity.getCommandes().size());

        if (livraisonEntity.getCommandes() == null || livraisonEntity.getCommandes().isEmpty()) {
            responseDTO.setMontant(10);
            responseDTO.setTdmTheorique(15);
            responseDTO.setDistanceParcourue(100);
        }

        else {

            Set<CommandeResponseDTO> commandeResponseDTOS = livraisonEntity.getCommandes().stream()
                    .map(commandeMapper::toResponse)
                    .collect(Collectors.toSet());

            //CHANGER truc calculable
            responseDTO.setMontant(10);
            responseDTO.setTdmTheorique(15);
            responseDTO.setDistanceParcourue(100);

            responseDTO.setCommandes(commandeResponseDTOS);
        }
        return responseDTO;
    }

}
