package fr.uga.l3miage.integrator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.services.CommandeService;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LivraisonControllerTest {

    @Mock
    private LivraisonService livraisonService;

    @Mock
    private CommandeService commandeService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LivraisonController livraisonController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllLivraisons() {
        // Mocking
        List<LivraisonEntity> livraisons = Collections.singletonList(new LivraisonEntity());
        when(livraisonService.getAllLivraison()).thenReturn(livraisons);

        // Test
        ResponseEntity<List<LivraisonEntity>> response = livraisonController.getAllLivraisons();

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livraisons, response.getBody());
    }

    @Test
    void testGetLivraisonByReference() {
        // Mocking
        LivraisonEntity livraison = new LivraisonEntity();
        when(livraisonService.getLivraisonByReference("reference")).thenReturn(livraison);

        // Test
        ResponseEntity<LivraisonEntity> response = livraisonController.getLivraisonByReference("reference");

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livraison, response.getBody());
    }

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
    }
}
