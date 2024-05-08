package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CamionComponent;
import fr.uga.l3miage.integrator.mappers.CamionMapper;
import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamionService {
    private final CamionComponent camionComponent;
    private final CamionMapper camionMapper;

    public List<CamionResponseDTO> getAllCamions(){
        List<CamionEntity> camionEntityList = camionComponent.getAllCamions();
        return camionEntityList.stream()
                .map(camionMapper::toResponse)
                .collect(Collectors.toList());

    }
}
