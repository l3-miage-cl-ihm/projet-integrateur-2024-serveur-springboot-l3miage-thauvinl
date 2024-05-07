package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class LivraisonTestService{

    @MockBean
    private LivraisonComponent livraisonComponent;

    @SpyBean
    private LivraisonMapper livraisonMapper;

    @MockBean
    private AdresseMapper adresseMapper;

    @Autowired
    private LivraisonService livraisonService;

    @Test
    void getAllLivraison() {
        // Given
        LivraisonEntity livraisonEntity1 = new LivraisonEntity();
        LivraisonEntity livraisonEntity2 = new LivraisonEntity();
        List<LivraisonEntity> livraisons = Arrays.asList(livraisonEntity1, livraisonEntity2);

        LivraisonResponseDTO livraisonResponseDTO1 = LivraisonResponseDTO.builder().build();
        LivraisonResponseDTO livraisonResponseDTO2 = LivraisonResponseDTO.builder().build();
        when(livraisonComponent.getAllLivraison()).thenReturn(livraisons);
        when(livraisonMapper.toResponse(livraisonEntity1)).thenReturn(livraisonResponseDTO1);
        when(livraisonMapper.toResponse(livraisonEntity2)).thenReturn(livraisonResponseDTO2);

        // When
        List<LivraisonResponseDTO> result = livraisonService.getAllLivraison();

        // Then
        assertEquals(2, result.size());
        assertEquals(livraisonResponseDTO1, result.get(0));
        assertEquals(livraisonResponseDTO2, result.get(1));
    }

    @Test
    void getLivraisonByReference() throws NotFoundLivraisonEntityException {
        // Given
        String reference = "123";
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        LivraisonResponseDTO livraisonResponseDTO = LivraisonResponseDTO.builder().build();
        when(livraisonComponent.getLivraisonByReference(reference)).thenReturn(livraisonEntity);
        when(livraisonMapper.toResponse(livraisonEntity)).thenReturn(livraisonResponseDTO);

        // When
        LivraisonResponseDTO result = livraisonService.getLivraisonByReference(reference);

        // Then
        assertEquals(livraisonResponseDTO, result);
    }

    // Ajoutez d'autres tests pour les autres méthodes de LivraisonService si nécessaire
}
