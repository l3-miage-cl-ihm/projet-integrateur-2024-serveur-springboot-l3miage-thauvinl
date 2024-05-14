package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.CamionComponent;
import fr.uga.l3miage.integrator.mappers.CamionMapper;
import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import fr.uga.l3miage.integrator.services.CamionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class CamionServiceTest {

    @MockBean
    private CamionComponent camionComponent;

    @SpyBean
    private CamionMapper camionMapper;

    @Autowired
    private CamionService camionService;

    @Test
    void testGetAllCamionsSuccess() {
        // Given
        CamionEntity camion1 = CamionEntity.builder().immatriculation("111").build();
        CamionEntity camion2 = CamionEntity.builder().immatriculation("222").build();

        List<CamionEntity> camionEntityList = Arrays.asList(camion1, camion2);

        CamionResponseDTO camionResponseDTO1 = CamionResponseDTO.builder().immatriculation("111").build();
        CamionResponseDTO camionResponseDTO2 = CamionResponseDTO.builder().immatriculation("222").build();

        when(camionComponent.getAllCamions()).thenReturn(camionEntityList);
        when(camionMapper.toResponse(camion1)).thenReturn(camionResponseDTO1);
        when(camionMapper.toResponse(camion2)).thenReturn(camionResponseDTO2);

        // When
        List<CamionResponseDTO> result = camionService.getAllCamions();

        // Then
        assertEquals(2, result.size());
        assertEquals(camionResponseDTO1, result.get(0));
        assertEquals(camionResponseDTO2, result.get(1));
    }

    @Test
    void testGetAllCamionsEmptyList() {
        // Given
        when(camionComponent.getAllCamions()).thenReturn(Collections.emptyList());

        // When
        List<CamionResponseDTO> result = camionService.getAllCamions();

        // Then
        assertEquals(0, result.size());
    }

}
