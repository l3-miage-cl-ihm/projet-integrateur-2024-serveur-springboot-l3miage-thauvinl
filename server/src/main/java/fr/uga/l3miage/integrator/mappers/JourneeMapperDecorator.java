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
import java.util.Set;
import java.util.stream.Collectors;

public abstract class JourneeMapperDecorator implements  JourneeMapper{
    @Autowired
    @Qualifier("delegate")
    private JourneeMapper delegate;
    @Autowired
    private TourneeMapper tourneeMapper;

    @Override
    public JourneeEntity toEntity(JourneeCreationRequest request){
        JourneeEntity journee = delegate.toEntity(request);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = sdf.format(journee.getDate());
        journee.setReference("j0" + day + "G");
        journee.setTournees(new HashSet<>());
        return journee;
    }

    @Override
    public JourneeResponseDTO toResponseWithTournees(JourneeEntity journee){
        JourneeResponseDTO responseDTO = delegate.toResponseWithTournees(journee);

        // Transformer chaque LivraisonEntity en LivraisonResponseDTO
        if (journee.getTournees() == null || journee.getTournees().isEmpty()) {
            responseDTO.setTempsDeMontageTheorique(0);
            responseDTO.setMontant(0.0);
            responseDTO.setDistanceAParcourir(0.0);
        }
        else{
            Set<TourneeResponseDTO> tourneeResponseDTOS = journee.getTournees().stream()
                    .map(tourneeMapper::toResponse) // Utilisation de LivraisonMapper pour convertir
                    .collect(Collectors.toSet()); // Collecter les résultats dans un Set
            responseDTO.setTourneeResponseDTOS(tourneeResponseDTOS); // Affecter les DTOs de livraison au DTO de la tournée
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

        return responseDTO;
    }
}
