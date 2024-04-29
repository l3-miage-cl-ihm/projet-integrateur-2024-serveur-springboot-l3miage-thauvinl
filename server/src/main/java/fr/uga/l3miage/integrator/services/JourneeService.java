package fr.uga.l3miage.integrator.services;
import antlr.ASTFactory;
import ch.qos.logback.classic.Logger;
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
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JourneeService {

    private final JourneeComponent journeeComponent;
    private final TourneeComponent tourneeComponent;
    private final JourneeMapper journeeMapper;
    private final TourneeMapper tourneeMapper;

    public JourneeResponseDTO addTourneeInJournee(String journeeReference, TourneeCreationRequest request){
        try {
            TourneeEntity tourneeEntity = tourneeMapper.toEntity(request);
            return journeeMapper.toResponseWithTournees(journeeComponent.addTourneeInJournee(journeeReference, tourneeEntity));
        }catch (NotFoundJourneeEntityException e){
            throw new AddingTourneeRestException(e.getMessage());
        }
    }
    public JourneeResponseDTO getJournee(String reference) {
        try{
            return journeeMapper.toResponseWithTournees(journeeComponent.getJourneeByRef(reference));
        }catch (NotFoundJourneeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }



    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest) {
        try{
            JourneeEntity journeeEntity = journeeMapper.toEntity(journeeCreationRequest);
            return journeeMapper.toResponseWithTournees(journeeComponent.createJournee(journeeEntity));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create journée: " + e.getMessage(), e);
        }
    }

}
