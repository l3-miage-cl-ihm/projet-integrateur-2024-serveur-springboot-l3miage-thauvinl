package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.services.CommandeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
public class CommandeServiceTest {
     @Autowired
    private CommandeService commandeService;
    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.initMocks(this);
        commandeService.setCommandeRepository(commandeRepository);
        commandeService.setClientRepository(clientRepository);
    }



    @Test
    public void testFindClientAdressByCommande() {
        // Création d'une commande avec une référence
        CommandeEntity commande = new CommandeEntity();
        commande.setReference("CMD123");
        Set<CommandeEntity> commandes = new HashSet<>();
        commandes.add(commande);
        commandeRepository.save(commande);
        commandeService.setCommandeRepository(commandeRepository);
        // Création d'un client avec une adresse
        Adresse adresse = new Adresse();
        adresse.setVille("Grenoble");
        ClientEntity client = new ClientEntity();
        client.setAdresse(adresse);
        clientRepository.save(client);
        commandeService.setClientRepository(clientRepository);
        // Définition du comportement du clientRepository mock
       when(clientRepository.findByCommandesReference("CMD123")).thenReturn(client);

        // Définition du comportement du commandeRepository mock
        when(commandeRepository.findCommandeEntitiesByReference("CMD123")).thenReturn(commandes);

        // Appel de la méthode à tester
        Adresse adresseTrouvee = commandeService.findClientAdressByCommande(commande);

        // Vérification que l'adresse retournée est celle du client associé à la commande
        assertEquals(adresse, adresseTrouvee);

        // Vérification que la méthode findByCommandesReference du clientRepository a bien été appelée
        verify(clientRepository).findByCommandesReference("CMD123");

        // Vérification que la méthode findCommandeByReference du commandeRepository a bien été appelée
        verify(commandeRepository).findCommandeEntitiesByReference("CMD123");
    }
}
