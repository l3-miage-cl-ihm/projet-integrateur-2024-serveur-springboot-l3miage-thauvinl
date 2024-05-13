package fr.uga.l3miage.integrator.components;


import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.repositories.CamionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CamionComponentTest {

    @MockBean
    private CamionRepository camionRepository;

    @Autowired
    private CamionComponent camionComponent;

    @Test
    void testGetAllCamionsSuccess() {
        //Given
        CamionEntity camion1 = CamionEntity
                .builder()
                .immatriculation("007")
                .build();

        CamionEntity camion2 = CamionEntity
                .builder()
                .immatriculation("001")
                .build();

        List<CamionEntity> camions = new ArrayList<>();
        camions.add(camion1);
        camions.add(camion2);
        when(camionRepository.findAll()).thenReturn(camions);

        //When
        List<CamionEntity> result = camionComponent.getAllCamions();

        //Then
        assertTrue((result.size() == 2));
        assertTrue(result.contains(camion1));
        assertTrue(result.contains(camion2));
    }

    @Test
    void testGetAllCamionsEmpty() {
        //Given
        when(camionRepository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<CamionEntity> result = camionComponent.getAllCamions();

        //Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testgetCamionByRefSuccess(){
        //Given
        String ref = "007";
        CamionEntity expectedCamion = CamionEntity
                .builder()
                .immatriculation(ref)
                .build();
        when(camionRepository.findCamionEntityByImmatriculation(ref)).thenReturn(Optional.of(expectedCamion));

        //When
        CamionEntity result = camionComponent.getCamionByRef(ref);

        //Then
        assertEquals(expectedCamion, result);
    }

    @Test
    void testGetCamionByRefFailed(){
        //Given
        String ref = "007";
        when(camionRepository.findCamionEntityByImmatriculation(ref)).thenReturn(Optional.empty());

        //When-Then
        assertThrows(RuntimeException.class, () -> camionComponent.getCamionByRef(ref));
    }

}
