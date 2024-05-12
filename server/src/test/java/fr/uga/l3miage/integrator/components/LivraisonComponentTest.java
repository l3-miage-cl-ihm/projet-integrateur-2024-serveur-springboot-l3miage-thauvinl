package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class LivraisonComponentTest {

    @MockBean
    private LivraisonRepository livraisonRepository;

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private CommandeComponent commandeComponent;
    @Autowired
    private LivraisonComponent livraisonComponent;


    @Test
    public void getAllLivraisonOK(){
        LivraisonEntity livraison=LivraisonEntity.builder().build();
        when(livraisonRepository.findAll()).thenReturn(List.of(livraison));
        // when - then
        assertDoesNotThrow(()->livraisonComponent.getAllLivraison());
    }

    @Test
    public void getLivraisonByReferenceOK(){
        LivraisonEntity livraison=LivraisonEntity.builder().build();
        when(livraisonRepository.findLivraisonEntityByReference(any())).thenReturn(Optional.of(livraison));
        assertDoesNotThrow(()->livraisonComponent.getLivraisonByReference(any()));
    }
    @Test
    public void getLivraisonByReferenceNOTOK(){
        //LivraisonEntity livraison=LivraisonEntity.builder().build();
        when(livraisonRepository.findLivraisonEntityByReference(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundLivraisonEntityException.class,()->livraisonComponent.getLivraisonByReference(any()));
    }

    @Test
    void countElementsInRepo() {
        // Given
        when(livraisonRepository.count()).thenReturn(10L);
        // When
        long count = livraisonComponent.countElementsInRepo();

        // Then
        assertEquals(10L, count);
    }

    @Test
    void save() {
        // Given
        LivraisonEntity livraisonEntityToSave = new LivraisonEntity();

        // When
        when(livraisonRepository.save(livraisonEntityToSave)).thenReturn(livraisonEntityToSave);

        LivraisonEntity savedLivraisonEntity = livraisonComponent.save(livraisonEntityToSave);

        // Then
        assertEquals(livraisonEntityToSave, savedLivraisonEntity);
        verify(livraisonRepository, times(1)).save(livraisonEntityToSave);
    }
    @Test
    void getAdresseClientFromLivraisonOK() throws NotFoundClientEntityExeption {
        // Given
        Adresse adresseClient = new Adresse();
        adresseClient.setAdresse("rue centrale");
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        CommandeEntity commande = new CommandeEntity();
        ClientEntity client = new ClientEntity();
        client.setAdresse(adresseClient);
        client.setCommandes(Set.of(commande));
        clientRepository.save(client);

        livraisonEntity.setCommandes(Set.of(commande));

         // When
        when(commandeComponent.findByCommandesReference(commande)).thenReturn(client);
        when(commandeComponent.findClientAdressByCommande(any())).thenReturn(adresseClient);


        Adresse adresseResult = livraisonComponent.getAdresseClientFromLivraison(livraisonEntity);

        // Then
        assertNotNull(adresseResult);
        assertEquals(adresseClient, adresseResult);
    }

    @Test
    void getAdresseClientFromLivraisonNOTOK() throws NotFoundClientEntityExeption {
        // Given

        LivraisonEntity livraisonEntity = new LivraisonEntity();
        CommandeEntity commande = new CommandeEntity();
        ClientEntity client = new ClientEntity();
        client.setCommandes(Set.of(commande));
        clientRepository.save(client);

        livraisonEntity.setCommandes(Set.of(commande));

        // When
        when(commandeComponent.findByCommandesReference(commande)).thenReturn(client);
        when(commandeComponent.findClientAdressByCommande(any())).thenReturn(null);


        Adresse adresseResult = livraisonComponent.getAdresseClientFromLivraison(livraisonEntity);

        // Then
        assertNull(adresseResult);

    }

    @Test
    void getProduitsGrpdByQuantitéOK() throws Exception {
        // Given

        LivraisonEntity livraisonEntity = new LivraisonEntity();
        livraisonEntity.setCommandes(Set.of(new CommandeEntity()));
        when(livraisonRepository.findLivraisonEntityByReference(any())).thenReturn(Optional.of(livraisonEntity));
        ProduitEntity produit=new ProduitEntity();
        CommandeComponent.ProduitQuantite prodQtt = new CommandeComponent.ProduitQuantite(produit,1);
        when(commandeComponent.getProduitsGroupedByQtt(any())).thenReturn(Set.of(prodQtt));

        // When
        Set<CommandeComponent.ProduitQuantite> produits = livraisonComponent.getProduitsGrpdByQuantité("ref123");

        // Then
        assertEquals(1,produits.size());
        assertEquals(Set.of(prodQtt),produits);

    }

    @Test
    void getProduitsGrpdByQuantitéNOTOK() throws Exception {
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        livraisonEntity.setCommandes(Set.of(new CommandeEntity()));
        when(livraisonRepository.findLivraisonEntityByReference(any())).thenReturn(Optional.of(livraisonEntity));
        when(commandeComponent.getProduitsGroupedByQtt(any())).thenReturn(Set.of());

        // When
        Set<CommandeComponent.ProduitQuantite> produits = livraisonComponent.getProduitsGrpdByQuantité("ref123");


        // Then
        assertTrue(produits.isEmpty()); // Vérifie que la liste de produits est vide

    }

    @Test
    void updateEtatOK() throws NotFoundLivraisonEntityException {
        // Given
        String reference = "REF123";
        String etatS="planifiee";
        EtatDeLivraison etat=EtatDeLivraison.planifiee;

        LivraisonEntity livraisonEntity = new LivraisonEntity();
        livraisonEntity.setReference(reference);
        // When
        when(livraisonRepository.findLivraisonEntityByReference(any())).thenReturn(Optional.of(livraisonEntity));
        when(livraisonRepository.save(any(LivraisonEntity.class))).thenReturn(livraisonEntity);


        LivraisonEntity updatedLivraison = livraisonComponent.updateEtat(reference, etatS);

        // Then
        assertEquals(etat, updatedLivraison.getEtat());

    }

    @Test
    void updateEtatNOTOK() {
        // Given
        String reference = "REF123";
        String etatS="planifiee";


        LivraisonEntity livraisonEntity = new LivraisonEntity();
        livraisonEntity.setReference(reference);
        // When
        when(livraisonRepository.findLivraisonEntityByReference(reference)).thenReturn(Optional.empty());
        when(livraisonRepository.save(any(LivraisonEntity.class))).thenReturn(livraisonEntity);


        // When / Then
        NotFoundLivraisonEntityException exception = assertThrows(NotFoundLivraisonEntityException.class,
                () -> livraisonComponent.updateEtat(reference, etatS));

        assertEquals("La livraison de référence REF123 n'a pas été trouvée", exception.getMessage());

    }
}
