package fr.uga.l3miage.integrator.service;
import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.CommandeMapper;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
class CommandeServiceTest {
    @MockBean
    private CommandeComponent commandeComponent;
    @MockBean
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeService commandeService;

    @SpyBean
    private CommandeMapper commandeMapper;
    @SpyBean
    private AdresseMapper adresseMapper;

    @Test
    public void getCommandeByRefOK() throws NotFoundCommandeEntityException {
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .build();
        CommandeResponseDTO commandeResponseDTO=CommandeResponseDTO.builder().reference("ref123").build();

        when(commandeComponent.getCommandeByReference("ref123")).thenReturn(commandeEntity);
        when(commandeMapper.toResponse(commandeEntity)).thenReturn(commandeResponseDTO);


        CommandeResponseDTO result = commandeService.getCommandeByReference("ref123");


        assertNotNull(result);

        assertEquals("ref123", result.getReference());
    }
    @Test
    public void getCommandeByRefNOTOK() throws NotFoundCommandeEntityException {

            when(commandeComponent.getCommandeByReference(any(String.class))).thenThrow(NotFoundCommandeEntityException.class);
            assertThrows(NotFoundEntityRestException.class, () -> commandeService.getCommandeByReference("ref123"));
    }


    @Test
    void testGetAllCommande() {
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .build();
        CommandeEntity commandeEntity2 = CommandeEntity.builder()
                .reference("ref345")
                .build();
        CommandeResponseDTO commandeResponseDTO=CommandeResponseDTO.builder().reference("ref123").build();
        CommandeResponseDTO commandeResponseDTO2=CommandeResponseDTO.builder().reference("ref345").build();
        List<CommandeEntity> commandeEntityList= Arrays.asList(commandeEntity,commandeEntity2);
        when(commandeComponent.getAllCommandes()).thenReturn(commandeEntityList);
        when(commandeMapper.toResponse(commandeEntity)).thenReturn(commandeResponseDTO);
        when(commandeMapper.toResponse(commandeEntity2)).thenReturn(commandeResponseDTO2);

        Set<CommandeResponseDTO> result=commandeService.getAllCommandes();
        // Then
        assertThat(result).containsExactlyInAnyOrder(commandeResponseDTO, commandeResponseDTO2);

    }
    @Test
    void getCommandesGroupedByClientOK(){
        // Given
        Adresse adresse1 = Adresse.builder().adresse("Adresse 1").build();
        Adresse adresse2 = Adresse.builder().adresse("Adresse 2").build();
        CommandeEntity commande1 = CommandeEntity.builder().reference("ref1").build();
        CommandeEntity commande2 = CommandeEntity.builder().reference("ref2").build();
        List<CommandeEntity> commandes1 = Arrays.asList(commande1);
        List<CommandeEntity> commandes2 = Arrays.asList(commande2);

        Map<Adresse, List<CommandeEntity>> groupedCommandes = new HashMap<>();
        groupedCommandes.put(adresse1, commandes1);
        groupedCommandes.put(adresse2, commandes2);

        when(commandeComponent.getCommandesGroupedByClient()).thenReturn(groupedCommandes);

        // When
        Set<ClientCommandesPairResponseDTO> result = commandeService.getCommandesGroupedByClient();

        // Then
        assertThat(result).hasSize(2);


        assertThat(result).anySatisfy(pair -> {
            assertThat(pair.getAdresse()).isEqualTo(adresseMapper.toResponse(adresse1));
            assertThat(pair.getCommandes()).hasSize(1).containsExactlyInAnyOrder(commandeMapper.toResponse(commande1));
        });


        assertThat(result).anySatisfy(pair -> {
            assertThat(pair.getAdresse()).isEqualTo(adresseMapper.toResponse(adresse2));
            assertThat(pair.getCommandes()).hasSize(1).containsExactlyInAnyOrder(commandeMapper.toResponse(commande2));
        });
    }

    @Test
    void testUpdateEtatOK() throws NotFoundCommandeEntityException {
        // Given
        String reference = "ref123";
        EtatDeCommande etatDeCommande2=EtatDeCommande.livree;
        CommandeEntity commandeEntity2 = CommandeEntity.builder()
                .reference(reference)
                .etat(etatDeCommande2)
                .build();
        CommandeResponseDTO commandeResponseDTO = CommandeResponseDTO.builder()
                .reference(reference)
                .etat("livree")
                .build();

        when(commandeComponent.updateEtat(reference, "livree")).thenReturn(commandeEntity2);
        when(commandeMapper.toResponse(commandeEntity2)).thenReturn(commandeResponseDTO);

        // When
        CommandeResponseDTO result = commandeService.updateEtat(reference, "livree");

        // Then
        assertThat(result).isEqualTo(commandeResponseDTO);
    }
    @Test
    void testUpdateEtatNotOK() throws NotFoundCommandeEntityException {
        // Given
        String reference = "ref123";
        String nouvelEtat = "livree";

        when(commandeComponent.updateEtat(reference, nouvelEtat)).thenThrow(new NotFoundCommandeEntityException("Commande non trouvée"));

        // When / Then
        assertThatThrownBy(() -> commandeService.updateEtat(reference, nouvelEtat))
                .isInstanceOf(NotFoundEntityRestException.class)
                .hasMessage("Commande non trouvée");
    }
    @Test
    void testUpdateDateDeLivraisonOK() throws NotFoundCommandeEntityException {
        // Given
        String reference = "ref123";
        LocalDateTime nouvelleDate = LocalDateTime.of(2024, 5, 10, 0, 0);
        String dateAsString = nouvelleDate.toString();
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference(reference)
                .dateDeLivraisonEffective(nouvelleDate)
                .build();
        CommandeResponseDTO commandeResponseDTO = CommandeResponseDTO.builder()
                .reference(reference)
                .dateDeLivraisonEffective(nouvelleDate)
                .build();

        when(commandeComponent.updateDateDeLivraison(reference, dateAsString)).thenReturn(commandeEntity);
        when(commandeMapper.toResponse(commandeEntity)).thenReturn(commandeResponseDTO);

        // When
        CommandeResponseDTO result = commandeService.updateDateDeLivraison(reference, dateAsString);

        // Then
        assertThat(result).isEqualTo(commandeResponseDTO);
    }
    @Test
    void testUpdateDateDeLivraisonNotFound() throws NotFoundCommandeEntityException {
        // Given
        String reference = "ref123";
        String nouvelleDate = "2024-05-10";

        when(commandeComponent.updateDateDeLivraison(reference, nouvelleDate)).thenThrow(new NotFoundCommandeEntityException("Commande non trouvée"));

        // When / Then
        assertThatThrownBy(() -> commandeService.updateDateDeLivraison(reference, nouvelleDate))
                .isInstanceOf(NotFoundEntityRestException.class)
                .hasMessage("Commande non trouvée");
    }
}

