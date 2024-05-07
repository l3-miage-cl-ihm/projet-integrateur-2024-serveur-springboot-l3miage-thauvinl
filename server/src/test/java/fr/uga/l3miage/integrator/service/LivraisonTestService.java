package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.mappers.ProduitMapper;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.ProduitQuantiteResponseDTO;
import fr.uga.l3miage.integrator.responses.ProduitResponseDTO;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class LivraisonTestService{


    @MockBean
    private LivraisonComponent livraisonComponent;

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
    public void getLivraisonByReference() throws NotFoundLivraisonEntityException {
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
    public void getAdresseClientFromLivraison() throws NotFoundLivraisonEntityException {
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
        assertEquals(1,totProd.size());
        ProduitResponseDTO produitResp = totProd.stream().toList().get(0).getProduit();
        assertEquals(produitResponseDTO, produitResp);

    }

    @Test
    public void updateEtat() throws NotFoundLivraisonEntityException {
        LivraisonEntity livraisonEntity1 = new LivraisonEntity();
        livraisonComponent.save(livraisonEntity1);
        livraisonEntity1.setEtat(EtatDeLivraison.enDechargement);
        LivraisonEntity livraisonEntity2 = new LivraisonEntity();
        LivraisonResponseDTO livraisonResponseDTO= LivraisonResponseDTO.builder().build();
        when(livraisonComponent.updateEtat(any(),anyString())).thenReturn(livraisonEntity2);
        when(livraisonMapper.toResponse(any())).thenReturn(livraisonResponseDTO);

        LivraisonResponseDTO livraisonResponseDTO1=livraisonService.updateEtat("r432","planifiee");
        assertEquals(livraisonResponseDTO,livraisonResponseDTO1);


    }



}
