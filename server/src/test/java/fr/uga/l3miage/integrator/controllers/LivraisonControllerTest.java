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


    @Test
    void canGetAllLivraisons() {
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").build();
        livraisonRepository.save(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();
        LivraisonResponseDTO livraisonResponseDTO=LivraisonResponseDTO
                .builder()
                .reference("ref123")
                .commandes(Set.of())
                .distanceAParcourir(0.0)
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
                                                .distanceAParcourir(0.0)
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
    /*
    @Test
    void testGetAdresseClientFromLivraison()  {

        CommandeEntity commande= CommandeEntity.builder().reference("cmd123").build();
        commandeRepository.save(commande);
        Set<CommandeEntity> commandeEntities=new HashSet<>();
        commandeEntities.add(commande);
        Adresse adresse= Adresse.builder().adresse("13 rue central").ville("Grenoble").codePostal("38000").build();
        ClientEntity client=ClientEntity.builder().email("test@gmail.com").adresse(adresse).commandes(commandeEntities).build();
        clientRepository.save(client);
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").commandes(commandeEntities).build();
        livraisonRepository.save(livraisonEntity);
        commande.setLivraison(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        AdresseResponseDTO adresseResponseDTO= AdresseResponseDTO.builder().adresse("13 rue central").ville("Grenoble").codePostal("38000").build();
        ResponseEntity<AdresseResponseDTO> actual=testRestTemplate.exchange("/api/livraisons/adresseFromLivraison/{reference}", HttpMethod.GET, new HttpEntity<>(null, headers),AdresseResponseDTO.class,urlParams);
       System.out.println("adresse client"+client.getAdresse());
       System.out.println("adresse dto expected"+ adresseResponseDTO);
       System.out.println("livraisons commande"+livraisonEntity.getCommandes());
       System.out.println("client commande"+client.getCommandes());
       System.out.println("livraisons commande" + commande.getLivraison());
       System.out.println("adresse dto actual"+actual);
       Optional<LivraisonEntity> livraison=livraisonRepository.findLivraisonEntityByReference("ref123");
       if (!livraison.isEmpty()){
           System.out.println("livraison trouvé commande"+livraison.toString());
       }


        //assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(actual.getBody()).isEqualTo(adresseResponseDTO);

    }*/

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
                .distanceAParcourir(0.0)
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
    @Test
    public void updateHeureOK(){
        LivraisonEntity livraisonEntity = LivraisonEntity.builder().reference("ref123").build();
        livraisonRepository.save(livraisonEntity);

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        Time t=new Time(16,00,00);

        LivraisonResponseDTO livraisonResponseDTO=LivraisonResponseDTO
                .builder()
                .reference("ref123")
                .commandes(Set.of())
                .heureDeLivraisonEffective(t)
                .tdmTheorique(0)
                .distanceAParcourir(0.0)
                .montant(0.0)
                .build();
        ResponseEntity<LivraisonResponseDTO> actual = testRestTemplate.exchange("/api/livraisons/updateHeure/{reference}"
                , HttpMethod.PATCH, new HttpEntity<>(t, headers), LivraisonResponseDTO.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(livraisonResponseDTO);
    }
    @Test
    public void updateHeureNOTOK(){

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "ref123");
        Time t=new Time(16,00,00);

        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/livraisons/updateHeure/ref123")
                .errorMessage("La livraison de référence ref123 n'a pas été trouvée")
                .build();


        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/livraisons/updateHeure/{reference}"
                , HttpMethod.PATCH, new HttpEntity<>(t, headers), NotFoundErrorResponse.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }
}
