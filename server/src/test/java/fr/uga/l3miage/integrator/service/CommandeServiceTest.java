package fr.uga.l3miage.integrator.service;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.services.CommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommandeServiceTest {
/*
    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CommandeService commandeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCommandeByReference() {
        // Mocking
        Set<CommandeEntity> expectedCommandes = new HashSet<>();
        when(commandeRepository.findCommandeEntitiesByReference("reference")).thenReturn(expectedCommandes);

        // Test
        Set<CommandeEntity> commandes = commandeService.getCommandeByReference("reference");

        // Assertion
        assertEquals(expectedCommandes, commandes);
    }

    @Test
    void testGetAllCommandeByLivraison() {
        // Mocking
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        Set<CommandeEntity> expectedCommandes = new HashSet<>();
        when(commandeRepository.findCommandeEntitiesByLivraison(livraisonEntity)).thenReturn(expectedCommandes);

        // Test
        Set<CommandeEntity> commandes = commandeService.getAllCommandeByLivraison(livraisonEntity);

        // Assertion
        assertEquals(expectedCommandes, commandes);
    }

    @Test
    void testFindClientAdressByCommande() {
        // Mocking
        CommandeEntity commandeEntity = new CommandeEntity();
        ClientEntity clientEntity = new ClientEntity();
        Adresse expectedAdresse = new Adresse();
        expectedAdresse.setVille("Grenoble");
        clientEntity.setAdresse(expectedAdresse);
        when(clientRepository.findClientEntityByCommandes(commandeEntity)).thenReturn(clientEntity);

        // Test
        Adresse adresse = commandeService.findClientAdressByCommande(commandeEntity);

        // Assertion
        assertEquals(expectedAdresse, adresse);
    }*/
}
