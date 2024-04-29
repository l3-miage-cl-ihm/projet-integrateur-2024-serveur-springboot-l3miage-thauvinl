package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.SimpleDateFormat;
import java.util.HashSet;

public abstract class JourneeMapperDecorator implements  JourneeMapper{
    @Autowired
    @Qualifier("delegate")
    private JourneeMapper delegate;

    @Autowired
    private TourneeMapper mapper;
    @Override
    public JourneeEntity toEntity(JourneeCreationRequest request){
        JourneeEntity journee = delegate.toEntity(request);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = sdf.format(journee.getDate());
        journee.setReference("j0" + day + "G");
        journee.setTournees(new HashSet<>());
        for(TourneeCreationRequest tournee : request.getTournees()){
            journee.addTournee(mapper.toEntity(tournee));
        }
        return journee;
    }

    @Override
    public JourneeResponseDTO toResponseWithTournees(JourneeEntity journee){
        JourneeResponseDTO responseDTO = delegate.toResponseWithTournees(journee);
        if (journee.getTournees().isEmpty()) {
            responseDTO.setTempsDeMontageTheorique(0);
            responseDTO.setMontant(0.0);
            responseDTO.setDistanceAParcourir(0.0);
        }
        else {
            responseDTO.setTempsDeMontageTheorique(responseDTO.getTourneeResponseDTOS().stream()
                    .mapToInt(TourneeResponseDTO::getTempsDeMontageTheorique)
                    .sum());
            responseDTO.setMontant(responseDTO.getTourneeResponseDTOS().stream()
                    .mapToDouble(TourneeResponseDTO::getMontant)
                    .sum());
            responseDTO.setDistanceAParcourir(responseDTO.getTourneeResponseDTOS().stream()
                    .mapToDouble(TourneeResponseDTO::getDistanceAParcourir)
                    .sum());
        }
        System.out.println(responseDTO.getReference());
        return responseDTO;
    }
}
