package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.TourneeEndpoints;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import fr.uga.l3miage.integrator.services.JourneeService;
import fr.uga.l3miage.integrator.services.TourneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class TourneeController implements TourneeEndpoints {

    private  final TourneeService tourneeService;

    //@Override
    /*public TourneeResponseDTO createTournee(TourneeCreationRequest tourneeCreationRequest) {
        return tourneeService.createTournee(tourneeCreationRequest);
    }*/

    @Override
    public TourneeResponseDTO getTourneeByEmploye(String trigramme){
        return tourneeService.getTourneeByEmploye(trigramme);
    }
    @Override
    public TourneeResponseDTO addLivraisonInTournee(String idTournee, LivraisonCreationRequest request) {
        return tourneeService.addLivraisonInTournee(idTournee,request);
    }


}
