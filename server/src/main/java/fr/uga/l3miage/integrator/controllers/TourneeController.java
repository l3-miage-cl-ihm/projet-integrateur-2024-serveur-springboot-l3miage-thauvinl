package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.TourneeEndpoints;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import fr.uga.l3miage.integrator.services.TourneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TourneeController implements TourneeEndpoints {

    private  final TourneeService tourneeService;
    @Override
    public TourneeResponseDTO getTourneeByEmploye(String trigramme){
        return tourneeService.getTourneeByEmploye(trigramme);
    }

    @Override
    public TourneeResponseDTO updateTdmEffectifTournee(String reference, Integer tdmEffectif){
        return tourneeService.updateTdmEffectifTournee(reference, tdmEffectif);
    }

    @Override
    public TourneeResponseDTO updateEtat(String reference, String nvEtat) {
        return tourneeService.updateEtat(reference, nvEtat);
    }
}
