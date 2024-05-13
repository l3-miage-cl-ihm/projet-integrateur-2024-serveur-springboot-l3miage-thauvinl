package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.repositories.CamionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CamionComponent {
    private final CamionRepository camionRepository;

    public List<CamionEntity> getAllCamions(){
        return camionRepository.findAll();
    }
    public CamionEntity getCamionByRef(String ref) {

        return camionRepository.findCamionEntityByImmatriculation(ref)
                .orElseThrow(() -> new RuntimeException("Camion not found with ref: " + ref));
    }

}
