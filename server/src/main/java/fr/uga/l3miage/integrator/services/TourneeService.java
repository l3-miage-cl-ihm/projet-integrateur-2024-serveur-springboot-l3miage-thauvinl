package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.EmployeMapper;
import fr.uga.l3miage.integrator.mappers.TourneeMapper;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourneeService {
    private final TourneeRepository tourneeRepository;
    private final JourneeRepository journeeRepository;
    private final TourneeMapper tourneeMapper;
    private final EmployeMapper employeMapper;
    private final TourneeComponent tourneeComponent;

    public TourneeResponseDTO createTournee(TourneeCreationRequest tourneeCreationRequest) {
        try {
            TourneeEntity tournee = tourneeMapper.toEntity(tourneeCreationRequest);
            tournee.setEmployeEntitySet(tourneeCreationRequest.getEmployes()
                    .stream()
                    .map(employeMapper::toEntity)
                    .collect(Collectors.toSet()));
            return tourneeMapper.toResponseWithEmployes(tourneeComponent.createTournee(tournee));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create tournee" + e.getMessage(), e);
        }
    }

    public TourneeResponseDTO getTourneeByEmploye(String trigramme){
        try {
            return tourneeMapper.toResponseWithEmployes(tourneeComponent.getTourneeByEmploye(trigramme));
        }catch(NotFoundTourneeEntityException | NotFoundEmployeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

}
