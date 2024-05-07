package fr.uga.l3miage.integrator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class LivraisonControllerTest {

    @SpyBean
    private LivraisonService livraisonService;
    @SpyBean
    private LivraisonComponent livraisonComponent;

    @Autowired
    private LivraisonController livraisonController;

   /* @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }*/

    @Test
    void canGetAllLivraisons() {
        // Given
        List<LivraisonResponseDTO> livraisons = List.of(LivraisonResponseDTO.builder().build(), LivraisonResponseDTO.builder().build());
        when(livraisonService.getAllLivraison()).thenReturn(livraisons);

        // When
        List<LivraisonResponseDTO> result = livraisonController.getAllLivraisons();

        // Then
        assertEquals(livraisons, result);
    }

    @Test
    void canGetLivraisonByReference() {
        // Given
        String reference = "ref123";
        LivraisonResponseDTO livraison = LivraisonResponseDTO.builder().build();
        LivraisonEntity livraisonEntity=LivraisonEntity.builder().reference("ref123").build();
        livraisonComponent.save(livraisonEntity);
        when(livraisonService.getLivraisonByReference(reference)).thenReturn(livraison);

        // When
        LivraisonResponseDTO result = livraisonController.getLivraisonByReference(reference);

        // Then
        assertEquals(livraison, result);
    }
/*
    @Test
    void testCountLivraisons() {
        // Mocking
        long count = 5;
        when(livraisonService.countElementsInRepo()).thenReturn(count);

        // Test
        ResponseEntity<Long> response = livraisonController.countLivraisons();

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(count, response.getBody());
    }

    @Test
    void testGetAdresseClientFromLivraison() throws JsonProcessingException {
        // Mocking
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        when(livraisonService.getLivraisonByReference(anyString())).thenReturn(livraisonEntity);

        Set<CommandeEntity> commandeEntitySet = new HashSet<>();
        CommandeEntity commandeEntity = new CommandeEntity();
        commandeEntitySet.add(commandeEntity);
        when(commandeService.getAllCommandeByLivraison(livraisonEntity)).thenReturn(commandeEntitySet);

        Adresse adresse = new Adresse();
        when(commandeService.findClientAdressByCommande(commandeEntity)).thenReturn(adresse);

        when(objectMapper.writeValueAsString(anyString())).thenReturn("reference");

        // Test
        ResponseEntity<Adresse> response = livraisonController.getAdresseClientFromLivraison("jsonData");

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adresse, response.getBody());
    }*/
}
