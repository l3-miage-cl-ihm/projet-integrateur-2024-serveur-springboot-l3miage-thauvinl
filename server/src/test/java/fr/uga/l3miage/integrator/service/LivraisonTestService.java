package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.mappers.ProduitMapper;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.ProduitQuantiteResponseDTO;
import fr.uga.l3miage.integrator.responses.ProduitResponseDTO;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class LivraisonTestService{


    @MockBean
    private LivraisonComponent livraisonComponent;
    @MockBean
    private LivraisonRepository livraisonRepository;
    @SpyBean
    private LivraisonMapper livraisonMapper;

    @SpyBean
    private AdresseMapper adresseMapper;

    @Autowired
    private LivraisonService livraisonService;
    @SpyBean
    private ProduitMapper produitMapper;

    @Test
    public void getAllLivraison() {
        // Given
        LivraisonEntity livraisonEntity1 = new LivraisonEntity();
        LivraisonEntity livraisonEntity2 = new LivraisonEntity();
        List<LivraisonEntity> livraisons = Arrays.asList(livraisonEntity1, livraisonEntity2);

        LivraisonResponseDTO livraisonResponseDTO1 = LivraisonResponseDTO.builder().build();
        LivraisonResponseDTO livraisonResponseDTO2 = LivraisonResponseDTO.builder().build();
        when(livraisonComponent.getAllLivraison()).thenReturn(livraisons);
        when(livraisonMapper.toResponse(livraisonEntity1)).thenReturn(livraisonResponseDTO1);
        when(livraisonMapper.toResponse(livraisonEntity2)).thenReturn(livraisonResponseDTO2);

        // When
        List<LivraisonResponseDTO> result = livraisonService.getAllLivraison();

        // Then
        assertEquals(2, result.size());
        assertEquals(livraisonResponseDTO1, result.get(0));
        assertEquals(livraisonResponseDTO2, result.get(1));
    }

    @Test
    public void getLivraisonByReferenceOK() throws NotFoundLivraisonEntityException {
        // Given
        String reference = "123";
        LivraisonEntity livraisonEntity = new LivraisonEntity();
        LivraisonResponseDTO livraisonResponseDTO = LivraisonResponseDTO.builder().build();
        when(livraisonComponent.getLivraisonByReference(reference)).thenReturn(livraisonEntity);
        when(livraisonMapper.toResponse(livraisonEntity)).thenReturn(livraisonResponseDTO);

        // When
        LivraisonResponseDTO result = livraisonService.getLivraisonByReference(reference);

        // Then
        assertEquals(livraisonResponseDTO, result);
    }
    @Test
    public void getLivraisonByReferenceNOTOK() throws NotFoundLivraisonEntityException {

        when(livraisonComponent.getLivraisonByReference(any())).thenThrow(NotFoundLivraisonEntityException.class);
        assertThrows(NotFoundEntityRestException.class, () -> livraisonService.getLivraisonByReference("ref123"));
    }

    @Test
    public void countElementsInRepo(){
        //Given
        LivraisonEntity livraisonEntity1 = new LivraisonEntity();
        LivraisonEntity livraisonEntity2 = new LivraisonEntity();
        livraisonComponent.save(livraisonEntity1);
        livraisonComponent.save(livraisonEntity2);
        //when
        when(livraisonComponent.countElementsInRepo()).thenReturn(2L);
        long nb= livraisonService.countElementsInRepo();
        //then
        assertEquals(2L,nb);

    }
    @Test
    public void getAdresseClientFromLivraison() throws NotFoundLivraisonEntityException, NotFoundClientEntityException {
        //Given
        LivraisonEntity livraisonEntity1 = new LivraisonEntity();
        livraisonComponent.save(livraisonEntity1);
        Adresse adresse=new Adresse();
        adresse.setAdresse("rue central");
        AdresseResponseDTO adresseResponseDTO=AdresseResponseDTO.builder().build();

        //when
        when(livraisonComponent.getLivraisonByReference(any())).thenReturn(livraisonEntity1);
        when(livraisonComponent.getAdresseClientFromLivraison(any())).thenReturn(adresse);
        when(adresseMapper.toResponse(any())).thenReturn(adresseResponseDTO);

        //then
        AdresseResponseDTO adresseResponseDTO2=livraisonService.getAdresseClientFromLivraison("ref123");
        assertEquals(adresseResponseDTO,adresseResponseDTO2);

    }

    @Test
    public void getProduitsGrpByQtt() throws Exception {
        LivraisonEntity livraisonEntity1 = new LivraisonEntity();
        livraisonComponent.save(livraisonEntity1);
        ProduitEntity prod= ProduitEntity.builder().build();
        ProduitResponseDTO produitResponseDTO=ProduitResponseDTO.builder().build();

        when(livraisonComponent.getLivraisonByReference(any())).thenReturn(livraisonEntity1);
        Set<CommandeComponent.ProduitQuantite> totalProd= Set.of(new CommandeComponent.ProduitQuantite(prod,1));
        when(livraisonComponent.getProduitsGrpdByQuantité(any())).thenReturn(totalProd);
        when(produitMapper.toResponse(any())).thenReturn(produitResponseDTO);

        Set<ProduitQuantiteResponseDTO> totProd=livraisonService.getProduitsGrpByQtt("ref123");
        ProduitResponseDTO produitResp = totProd.stream()
                .map(ProduitQuantiteResponseDTO::getProduit)
                .findFirst()
                .orElse(null);
        assertEquals(produitResponseDTO, produitResp);

    }

    @Test
    public void updateEtat() throws NotFoundLivraisonEntityException {
        LivraisonEntity livraisonEntity1 = LivraisonEntity.builder().reference("ref123").build();
        livraisonEntity1.setEtat(EtatDeLivraison.enDechargement);
       String nvEtat="effectuee";

        when(livraisonComponent.updateEtat(any(), anyString())).thenReturn(livraisonEntity1);
        livraisonEntity1.setEtat(EtatDeLivraison.valueOf(nvEtat));
        LivraisonResponseDTO expectedResponseDTO=livraisonMapper.toResponse(livraisonEntity1);
        LivraisonResponseDTO actualResponseDTO = livraisonService.updateEtat("ref123", "planifiee");

        assertEquals(expectedResponseDTO.getReference(), actualResponseDTO.getReference());
        assertEquals(expectedResponseDTO.getEtat(), actualResponseDTO.getEtat());
    }





}
