package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.AddingTourneeRestException;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JourneeService {

    private final JourneeComponent journeeComponent;
    private final TourneeComponent tourneeComponent;
    private final JourneeMapper journeeMapper;
    private final TourneeMapper tourneeMapper;

    public JourneeResponseDTO addTourneeInJournee(String journeeReference, String tourneeReference){
        try {
            TourneeEntity  tourneeEntity = tourneeComponent.getTourneeByRef(tourneeReference);
            JourneeEntity journeeEntity = journeeComponent.addTourneeInJournee(journeeReference, tourneeEntity);
            return journeeMapper.toResponseWithTournees(journeeEntity);
        }catch (NotFoundTourneeEntityException|NotFoundJourneeEntityException e){
            throw new AddingTourneeRestException(e.getMessage());
        }
    }
    public JourneeResponseDTO getJournee(String reference) {
        try{
            return journeeMapper.toResponseWithTournees(journeeComponent.getJournee(reference));
        }catch (NotFoundJourneeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }



    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest) {
        try{
            JourneeEntity journeeEntity = journeeMapper.toEntity(journeeCreationRequest);
            journeeEntity.setTournees(journeeCreationRequest.getTournees()
                    .stream()
                    .map(tourneeMapper::toEntity)
                    .collect(Collectors.toSet()));
            return journeeMapper.toResponseWithTournees(journeeComponent.createJournee(journeeEntity));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create journee: " + e.getMessage(), e);
        }
    }

}
