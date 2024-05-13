package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CommandeControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    LivraisonRepository livraisonRepository;

    @AfterEach
    public void clearDataBase(){
        clientRepository.deleteAll();
        commandeRepository.deleteAll();
    }

    @BeforeEach
    public void setup(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

   @Test
    public void canGetAllCommandes(){
       CommandeEntity commandeEntity = CommandeEntity.builder()
               .reference("ref123")
               .montant(0.0)
               .tdmTheorique(0)
               .build();
       CommandeEntity commandeEntity2 = CommandeEntity.builder()
               .reference("ref345")
               .montant(0.0)
               .tdmTheorique(0)
               .build();
       commandeRepository.save(commandeEntity);
       commandeRepository.save(commandeEntity2);
       CommandeResponseDTO commandeResponseDTO=CommandeResponseDTO.builder().reference("ref123").montant(0.0).tdmTheorique(0).build();
       CommandeResponseDTO commandeResponseDTO2=CommandeResponseDTO.builder().reference("ref345").montant(0.0).tdmTheorique(0).build();
       List<CommandeResponseDTO> expected= Arrays.asList(commandeResponseDTO,commandeResponseDTO2);

       final HttpHeaders headers = new HttpHeaders();
       ResponseEntity<List<CommandeResponseDTO>> actual = testRestTemplate.exchange("/api/commandes/AllCommandes", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<CommandeResponseDTO>>() {});
       assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
       assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
   }
    @Test
    public void canGetCommandeByReference(){
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity);
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        CommandeResponseDTO expected=CommandeResponseDTO.builder().reference("ref123").montant(0.0).tdmTheorique(0).build();
        ResponseEntity<CommandeResponseDTO> actual = testRestTemplate.exchange("/api/commandes/ref123", HttpMethod.GET, new HttpEntity<>(null, headers), CommandeResponseDTO.class,urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    public void canNOTGetCommandeByReference(){

        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/commandes/ref123")
                .errorMessage("La commande de référence ref123 est introuvable")
                .build();
        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/commandes/ref123", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class,urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    public void canGetCommandesGroupedByClient(){
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity);
        CommandeEntity commandeEntity2 = CommandeEntity.builder()
                .reference("ref345")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity2);
        CommandeEntity commandeEntity3 = CommandeEntity.builder()
                .reference("ref567")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity2);
        Adresse adresse= Adresse.builder().adresse("13 rue centrale").build();
        Adresse adresse2= Adresse.builder().adresse("14 rue centrale").build();
        ClientEntity clientEntity=ClientEntity.builder().email("test@gmail.com").adresse(adresse).commandes(Set.of(commandeEntity2,commandeEntity)).build();
        clientRepository.save(clientEntity);
        ClientEntity clientEntity2=ClientEntity.builder().email("test2@gmail.com").adresse(adresse2).commandes(Set.of(commandeEntity3)).build();
        clientRepository.save(clientEntity2);

        AdresseResponseDTO adresseResponseDTO=AdresseResponseDTO.builder().adresse("13 rue centrale").build();
        AdresseResponseDTO adresseResponseDTO2=AdresseResponseDTO.builder().adresse("14 rue centrale").build();


        CommandeResponseDTO commandeResponseDTO=CommandeResponseDTO.builder().reference("ref123")
                .montant(0.0)
                .tdmTheorique(0).build();
        CommandeResponseDTO commandeResponseDTO2=CommandeResponseDTO.builder().reference("ref345")
                .montant(0.0)
                .tdmTheorique(0).build();
        CommandeResponseDTO commandeResponseDTO3=CommandeResponseDTO.builder().reference("ref567")
                .montant(0.0)
                .tdmTheorique(0).build();

        ClientCommandesPairResponseDTO clientCommandesPairResponseDTO1=ClientCommandesPairResponseDTO.builder().adresse(adresseResponseDTO).commandes(Set.of(commandeResponseDTO,commandeResponseDTO2)).build();
        ClientCommandesPairResponseDTO clientCommandesPairResponseDTO2=ClientCommandesPairResponseDTO.builder().adresse(adresseResponseDTO2).commandes(Set.of(commandeResponseDTO3)).build();


        final HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Set<ClientCommandesPairResponseDTO>> actual = testRestTemplate.exchange("/api/commandes/CommandesByClient", HttpMethod.GET, new HttpEntity<>(null, headers),new ParameterizedTypeReference<Set<ClientCommandesPairResponseDTO>>() {});
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(Set.of(clientCommandesPairResponseDTO1,clientCommandesPairResponseDTO2));



    }
    @Test
    public void canUpdateEtat(){
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity);
        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        urlParams.put("nvEtat", "planifiee");
        CommandeResponseDTO expected=CommandeResponseDTO.builder().reference("ref123")
                .montant(0.0)
                .etat("planifiee")
                .tdmTheorique(0).build();
        ResponseEntity<CommandeResponseDTO> actual = testRestTemplate.exchange("/api/commandes/updateEtat/{reference}/{nvEtat}", HttpMethod.PATCH, new HttpEntity<>(null, headers), CommandeResponseDTO.class,urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);

    }
    @Test
    public void canUpdateDateDeLivraison(){
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .montant(0.0)
                .tdmTheorique(0)
                .dateDeCreation(LocalDateTime.of(2024,4,3,15,30,0))
                .build();
        commandeRepository.save(commandeEntity);
        String dateDeLivraisonEffective = "2024-04-15T01:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime livraisonEffective = LocalDateTime.parse(dateDeLivraisonEffective,formatter);
        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        urlParams.put("date", dateDeLivraisonEffective);
        CommandeResponseDTO expected=CommandeResponseDTO.builder().reference("ref123")
                .montant(0.0)
                .dateDeLivraisonEffective(livraisonEffective)
                .dateDeCreation(LocalDateTime.of(2024,4,3,15,30,0))
                .dureeDeLivraison(11)
                .tdmTheorique(0).build();
        ResponseEntity<CommandeResponseDTO> actual = testRestTemplate.exchange(
                "/api/commandes/updateDate/{reference}?date={date}",
                HttpMethod.PATCH,
                new HttpEntity<>(null, headers),
                CommandeResponseDTO.class,
                urlParams
        );
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void canGetAllCommandesByLivraison(){
        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference("ref123")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity);
        CommandeEntity commandeEntity2 = CommandeEntity.builder()
                .reference("ref345")
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        commandeRepository.save(commandeEntity2);
        Set<CommandeEntity> commandeEntities=new HashSet<>();
        commandeEntities.add(commandeEntity);
        commandeEntities.add(commandeEntity2);
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("refL123").commandes(commandeEntities).build();
        livraisonRepository.save(livraisonEntity);
        commandeEntity2.setLivraison(livraisonEntity);
        commandeEntity.setLivraison(livraisonEntity);
        commandeRepository.save(commandeEntity2);
        commandeRepository.save(commandeEntity);
        CommandeResponseDTO commandeResponseDTO=CommandeResponseDTO.builder().reference("ref123")
                .montant(0.0)
                .tdmTheorique(0).build();
        CommandeResponseDTO commandeResponseDTO2=CommandeResponseDTO.builder().reference("ref345")
                .montant(0.0)
                .tdmTheorique(0).build();

        final HttpHeaders headers = new HttpHeaders();
        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "refL123");
        ResponseEntity<Set<CommandeResponseDTO>> actual = testRestTemplate.exchange("/api/commandes/AllCommandesByLivraison/{reference}", HttpMethod.GET, new HttpEntity<>(null, headers),new ParameterizedTypeReference<Set<CommandeResponseDTO>>() {},urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(Set.of(commandeResponseDTO,commandeResponseDTO2));


    }
}
