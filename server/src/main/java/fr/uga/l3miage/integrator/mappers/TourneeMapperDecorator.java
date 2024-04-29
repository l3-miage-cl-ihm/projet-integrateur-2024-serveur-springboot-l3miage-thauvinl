package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class TourneeMapperDecorator implements TourneeMapper {
    @Autowired
    @Qualifier("delegate")
    private TourneeMapper delegate;

    @Override
    public TourneeEntity toEntity(TourneeCreationRequest request){
        TourneeEntity tournee = delegate.toEntity(request);
            tournee.setReference(request.getRefJournee().replaceFirst("^j", "t")+ "-" + tournee.getLettre());
            System.out.println(tournee.getReference());
        return tournee;
    }
    @Override
    public TourneeEntity toEntityWithJourneeRef(TourneeCreationRequest request, String ref){
        TourneeEntity tournee = delegate.toEntity(request);
        tournee.setReference(ref + tournee.getLettre());
        System.out.println(tournee.getReference());
        return tournee;
    }


    @Override
    public TourneeResponseDTO toResponseWithEmployes(TourneeEntity tournee){
        TourneeResponseDTO responseDTO = delegate.toResponseWithEmployes(tournee);

        if (tournee.getLivraisons()==null||tournee.getLivraisons().isEmpty()) {
            responseDTO.setTempsDeMontageTheorique(0);
            responseDTO.setMontant(0.0);
            responseDTO.setDistanceAParcourir(0.0);
        }
        else {
            responseDTO.setTempsDeMontageTheorique(tournee.getLivraisons().stream()
                    .mapToInt(LivraisonEntity::getTdmTheorique)
                    .sum());
            responseDTO.setMontant(tournee.getLivraisons().stream()
                    .mapToDouble(LivraisonEntity::getMontant)
                    .sum());
            responseDTO.setDistanceAParcourir(tournee.getLivraisons().stream()
                    .mapToDouble(LivraisonEntity::getDistanceParcourue)
                    .sum());
        }
        return responseDTO;
    }
}
