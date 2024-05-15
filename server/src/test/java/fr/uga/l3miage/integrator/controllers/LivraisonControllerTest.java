package fr.uga.l3miage.integrator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class LivraisonControllerTest {

    @Autowired
    LivraisonRepository livraisonRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

        @BeforeEach
        public void setup(){
            testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        }

    @AfterEach
    public void clearDataBase(){
        clientRepository.deleteAll();
        commandeRepository.deleteAll();
        livraisonRepository.deleteAll();
    }
    @Test
    void canGetAllLivraisons() {
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").build();
        livraisonRepository.save(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();
        LivraisonResponseDTO livraisonResponseDTO=LivraisonResponseDTO
                .builder()
                .reference("ref123")
                .commandes(Set.of())
                
                .montant(0.0)
                .tdmTheorique(0)
                .build();
        List<LivraisonResponseDTO> expected= new ArrayList<>();
        expected.add(livraisonResponseDTO);
        ResponseEntity<List<LivraisonResponseDTO>> actual = testRestTemplate.exchange("/api/livraisons/AllLivraisons", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<LivraisonResponseDTO>>() {});

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void canGetLivraisonByReference() {

        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").build();
        livraisonRepository.save(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");

        LivraisonResponseDTO livraisonResponseDTO=LivraisonResponseDTO
                                                .builder()
                                                .reference("ref123")
                                                .commandes(Set.of())
      .montant(0.0)

                                                .tdmTheorique(0)
                                                .build();
        ResponseEntity<LivraisonResponseDTO> actual=testRestTemplate.exchange("/api/livraisons/{reference}", HttpMethod.GET, new HttpEntity<>(null, headers), LivraisonResponseDTO.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(livraisonResponseDTO);
    }
    @Test
    void canNOTGetLivraisonByReference() {


        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");

        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/livraisons/ref123")
                .errorMessage("La livraison de référence ref123 n'a pas été trouvée")
                .build();
        ResponseEntity<NotFoundErrorResponse> actual=testRestTemplate.exchange("/api/livraisons/{reference}", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void testCountLivraisons() {

        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").build();
        livraisonRepository.save(livraisonEntity);
        final HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Long> expected = new ResponseEntity<>(1L, HttpStatus.OK);
        ResponseEntity<Long> actual = testRestTemplate.exchange("/api/livraisons/count",HttpMethod.GET, new HttpEntity<>(null, headers),Long.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(expected.getBody());
    }

    @Test
    void testGetAdresseClientFromLivraisonOK()  {
            //COMMANDES
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
        //LIVRAISONS

        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("refLIV123").commandes(Set.of(commandeEntity2,commandeEntity)).build();
        livraisonRepository.save(livraisonEntity);
        commandeEntity2.setLivraison(livraisonEntity);
        commandeEntity.setLivraison(livraisonEntity);

        //CLIENTS
        Adresse adresse= Adresse.builder().adresse("13 rue centrale").build();
        ClientEntity clientEntity=ClientEntity.builder().email("test@gmail.com").adresse(adresse).commandes(Set.of(commandeEntity2,commandeEntity)).build();
        clientRepository.save(clientEntity);

        AdresseResponseDTO adresseResponseDTO=AdresseResponseDTO.builder().adresse("13 rue centrale").build();
        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "refLIV123");

        ResponseEntity<AdresseResponseDTO> actual=testRestTemplate.exchange("/api/livraisons/adresseFromLivraison/{reference}"
                , HttpMethod.GET, new HttpEntity<>(null, headers), AdresseResponseDTO.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(adresseResponseDTO);


    }

    @Test
    void testGetAdresseClientFromLivraisonNOTOK()  {

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "refLIV123");
        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/livraisons/adresseFromLivraison/refLIV123")
                .errorMessage("La livraison de référence refLIV123 n'a pas été trouvée")
                .build();
        ResponseEntity<NotFoundErrorResponse> actual=testRestTemplate.exchange("/api/livraisons/adresseFromLivraison/{reference}"
                , HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    public void updateTdmOK(){
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").tdmEffectif(0).build();
        livraisonRepository.save(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");


        LivraisonResponseDTO livraisonResponseDTO=LivraisonResponseDTO
                .builder()
                .reference("ref123")
                .commandes(Set.of())
                .tdmEffectif(15)
                .tdmTheorique(0)

                .montant(0.0)
                .build();
        ResponseEntity<LivraisonResponseDTO> actual = testRestTemplate.exchange("/api/livraisons/updateTdm/{reference}"
                , HttpMethod.PATCH, new HttpEntity<>(15, headers), LivraisonResponseDTO.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(livraisonResponseDTO);
    }
    @Test
    public void updateTdmNOTOK(){

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");


        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/livraisons/updateTdm/ref123")
                .errorMessage("La livraison de référence ref123 n'a pas été trouvée")
                .build();


        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/livraisons/updateTdm/{reference}"
                , HttpMethod.PATCH, new HttpEntity<>(15, headers), NotFoundErrorResponse.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }
    /*
    @Test
    public void updateHeureOK(){
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").build();
        livraisonRepository.save(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        String dateDeLivraisonEffective = "2024-04-15T01:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime livraisonEffective = LocalDateTime.parse(dateDeLivraisonEffective,formatter);

        LivraisonResponseDTO livraisonResponseDTO=LivraisonResponseDTO
                .builder()
                .reference("ref123")
                .commandes(Set.of())
                .heureLivraison(livraisonEffective)
                .tdmTheorique(0)

                .montant(0.0)
                .build();
        ResponseEntity<LivraisonResponseDTO> actual = testRestTemplate.exchange("/api/livraisons/updateHeure/{reference}"
                , HttpMethod.PATCH, new HttpEntity<>(livraisonEffective, headers), LivraisonResponseDTO.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(livraisonResponseDTO);
    }
    @Test
    public void updateHeureNOTOK(){

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        String dateDeLivraisonEffective = "2024-04-15T01:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime livraisonEffective = LocalDateTime.parse(dateDeLivraisonEffective,formatter);

        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/livraisons/updateHeure/ref123")
                .errorMessage("La livraison de référence ref123 n'a pas été trouvée")
                .build();


        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/livraisons/updateHeure/{reference}"
                , HttpMethod.PATCH, new HttpEntity<>(livraisonEffective, headers), NotFoundErrorResponse.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }*/

}
