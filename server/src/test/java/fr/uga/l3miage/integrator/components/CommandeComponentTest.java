package fr.uga.l3miage.integrator.components;


import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.dataType.Adresse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeComponentTest {

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CommandeComponent commandeComponent;

    @Test
    public void testGetCommandeByReference() {
        // Given
        String reference = "REF123";
        CommandeEntity expectedCommandes = new CommandeEntity();
        // Mocking the repository behavior
        when(commandeRepository.findCommandeEntitiesByReference(reference)).thenReturn(expectedCommandes);

        // When
        CommandeEntity actualCommandes = commandeComponent.getCommandeByReference(reference);

        // Then
        assertEquals(expectedCommandes, actualCommandes);
        verify(commandeRepository, times(1)).findCommandeEntitiesByReference(reference);
    }

    @Test
    public void testGetAllCommandeByLivraison() {
        // Given
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        Set<CommandeEntity> expectedCommandes = new HashSet<>();
        // Mocking the repository behavior
        when(commandeRepository.findCommandeEntitiesByLivraison(livraisonEntity)).thenReturn(expectedCommandes);

        // When
        Set<CommandeEntity> actualCommandes = commandeComponent.getAllCommandeByLivraison(livraisonEntity);

        // Then
        assertEquals(expectedCommandes, actualCommandes);
        verify(commandeRepository, times(1)).findCommandeEntitiesByLivraison(livraisonEntity);
    }

    @Test
    public void testFindClientAdressByCommande() {
        // Given
        CommandeEntity commandeEntity = new CommandeEntity();
        ClientEntity clientEntity = new ClientEntity();
        Adresse expectedAdresse = new Adresse();
        expectedAdresse.setVille("Grenoble");
        clientEntity.setAdresse(expectedAdresse);

        // Mocking the repository behavior
        when(clientRepository.findClientEntityByCommandes(commandeEntity)).thenReturn(clientEntity);

        // When
        Adresse actualAdresse = commandeComponent.findClientAdressByCommande(commandeEntity);

        // Then
        assertEquals(expectedAdresse, actualAdresse);
        verify(clientRepository, times(1)).findClientEntityByCommandes(commandeEntity);
    }
}
