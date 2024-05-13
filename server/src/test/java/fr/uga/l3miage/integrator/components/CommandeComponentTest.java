package fr.uga.l3miage.integrator.components;



import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class CommandeComponentTest {

    @MockBean
    private CommandeRepository commandeRepository;

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private LivraisonRepository livraisonRepository;

    @Autowired
    private CommandeComponent commandeComponent;

    @Test
    public void testGetCommandeByReferenceOK() throws NotFoundCommandeEntityException {
        // Given
        String reference = "REF123";
        CommandeEntity commandeEntity = new CommandeEntity();

        when(commandeRepository.findCommandeEntityByReference(reference)).thenReturn(Optional.of(commandeEntity));

        // When
        CommandeEntity result = commandeComponent.getCommandeByReference(reference);

        // Then
        assertEquals(commandeEntity, result);

    }
    @Test
    void testFindCommandeEntityByReferenceNotOK() {

        String reference = "ref123";

        when(commandeRepository.findCommandeEntityByReference(reference))
                .thenReturn(Optional.empty());

        Optional<CommandeEntity> result = commandeRepository.findCommandeEntityByReference(reference);

        assertFalse(result.isPresent());
    }
   @Test
    public void testFindClientAdressByCommande(){
        Adresse adresse=new Adresse();
        adresse.setVille("grenoble");
        CommandeEntity commande=CommandeEntity.builder().reference("ref123").build();
        commandeRepository.save(commande);
        ClientEntity client=ClientEntity.builder().adresse(adresse).email("test@gmail.com").commandes(Set.of(commande)).build();
        clientRepository.save(client);
        when(clientRepository.findClientEntityByCommandes(commande)).thenReturn(Optional.of(client));
        Adresse adresse1=commandeComponent.findClientAdressByCommande(commande);

        assertEquals(adresse1,adresse);
   }

    @Test
    public void testGetAllCommandeByLivraison() throws NotFoundLivraisonEntityException {
        // Given
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        livraisonEntity.setReference("ref123");
        Set<CommandeEntity> expectedCommandes = new HashSet<>();
        livraisonEntity.setCommandes(expectedCommandes);
        when(livraisonRepository.findLivraisonEntityByReference(any())).thenReturn(Optional.of(livraisonEntity));
        // When
        Set<CommandeEntity> actualCommandes = commandeComponent.getAllCommandeByLivraison("ref123");
        // Then
        assertEquals(expectedCommandes, actualCommandes);

    }
    /*
    @Test
    void testGetCommandesGroupedByClient() {

        List<CommandeEntity> commandes = new ArrayList<>();
        Adresse adresse=new Adresse();
        adresse.setVille("grenoble");
        Adresse adresse2=new Adresse();
        adresse.setVille("SMH");
        CommandeEntity commande1 = CommandeEntity.builder().reference("cmd1").build();
        CommandeEntity commande2 = CommandeEntity.builder().reference("cmd2").build();
        commandes.add(commande1);
        commandes.add(commande2);
        ClientEntity client=ClientEntity.builder().adresse(adresse).email("test@gmail.com").commandes(Set.of(commande1,commande2)).build();
        clientRepository.save(client);
        when(clientRepository.findClientEntityByCommandes(commande1)).thenReturn(Optional.of(client));
        when(clientRepository.findClientEntityByCommandes(commande2)).thenReturn(Optional.of(client));

        when(commandeComponent.findClientAdressByCommande(eq(commande1))).thenReturn(adresse);
        when(commandeComponent.findClientAdressByCommande(eq(commande2))).thenReturn(adresse2);

        Map<Adresse, List<CommandeEntity>> result = commandeComponent.getCommandesGroupedByClient();

        assertEquals(2, result.size());

        assertTrue(result.containsKey(adresse));
        assertTrue(result.containsKey(adresse2));

    }*/
    @Test
    void testUpdateEtat() throws NotFoundCommandeEntityException {

        CommandeEntity commande = CommandeEntity.builder()
                .reference("ref123")
                .etat(EtatDeCommande.planifiee)
                .build();
        when(commandeRepository.save(any(CommandeEntity.class))).thenReturn(commande);
        when(commandeRepository.findCommandeEntityByReference(any())).thenReturn(Optional.of(commande));

        CommandeEntity updatedCommande = commandeComponent.updateEtat("ref123", "livree");

        assertEquals(EtatDeCommande.livree, updatedCommande.getEtat());
    }

    @Test
    void updateDateDeLivraisonEffectiveSuccess() throws NotFoundCommandeEntityException {
        CommandeEntity commande = CommandeEntity.builder()
                .reference("test")
                .dateDeCreation(LocalDateTime.of(2024,4,3,15,30,0))
                .build();
        String dateDeLivraisonEffective = "2024-04-15T01:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime livraisonEffective = LocalDateTime.parse(dateDeLivraisonEffective,formatter);
        when(commandeRepository.findCommandeEntityByReference(any(String.class))).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(CommandeEntity.class))).thenReturn(commande);
        CommandeEntity actual = commandeComponent.updateDateDeLivraison("test",dateDeLivraisonEffective);
        assertThat(actual.getReference()).isEqualTo(commande.getReference());
        assertThat(actual.getDateDeLivraisonEffective()).isEqualTo(livraisonEffective);
    }
}
