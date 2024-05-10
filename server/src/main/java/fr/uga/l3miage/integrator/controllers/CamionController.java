package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.CamionEndpoints;
import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import fr.uga.l3miage.integrator.services.CamionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CamionController implements CamionEndpoints {
    private final CamionService camionService;
    public List<CamionResponseDTO> getAllCamions(){
        return camionService.getAllCamions();
    }
}
