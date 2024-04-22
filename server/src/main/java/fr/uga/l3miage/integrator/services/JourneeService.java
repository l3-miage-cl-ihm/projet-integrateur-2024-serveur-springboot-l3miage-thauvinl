package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.mappers.JourneeMapper;
import fr.uga.l3miage.integrator.mappers.TourneeMapper;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JourneeService {

    private final JourneeComponent journeeComponent;
    private final JourneeMapper journeeMapper;
    private final TourneeMapper tourneeMapper;

    public Optional<JourneeEntity> findJourneeByReference(String reference) {
        // Implémentation de la logique pour trouver une Journee par sa référence
        return journeeComponent.findJourneeByReference(reference);
    }

    public List<JourneeEntity> findAllJournees(){
        return journeeComponent.findAllJournees();
    }

    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest) {
        try{
            JourneeEntity journeeEntity = journeeMapper.toEntity(journeeCreationRequest);
            journeeEntity.setTournees(journeeCreationRequest.getTournees()
                    .stream()
                    .map(tourneeMapper::toEntity)
                    .collect(Collectors.toSet()));
            return journeeMapper.toResponse(journeeComponent.createJournee(journeeEntity));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create journee: " + e.getMessage(), e);
        }
    }

    public List<TourneeEntity> getAllTourneesOfJournee(String reference) {
        // Implémentez la logique pour récupérer toutes les tournées associées à une journée spécifique
        // en utilisant la référence de la journée.
        return journeeComponent.getAllTourneesOfJournee(reference);
    }



}
