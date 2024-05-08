package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.ProduitMapper;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.ProduitQuantiteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LivraisonService {
    @Autowired
    private final LivraisonComponent livraisonComponent;
    private final LivraisonMapper livraisonMapper;
    private final AdresseMapper adresseMapper;
    private final ProduitMapper produitMapper;
    public List<LivraisonResponseDTO> getAllLivraison() {
        List<LivraisonEntity> livraisons = livraisonComponent.getAllLivraison();
        List<LivraisonResponseDTO> livraisonsDTO = new ArrayList<>();

        for (LivraisonEntity livraison : livraisons) {
            LivraisonResponseDTO livraisonDTO = livraisonMapper.toResponse(livraison);
            livraisonsDTO.add(livraisonDTO);
        }

        return livraisonsDTO;
    }

    public LivraisonResponseDTO getLivraisonByReference(String reference) {
        try{
            return livraisonMapper.toResponse(livraisonComponent.getLivraisonByReference(reference));
        }catch(NotFoundLivraisonEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }

    }
    public long countElementsInRepo(){
        return livraisonComponent.countElementsInRepo();
    }


    public AdresseResponseDTO getAdresseClientFromLivraison(String ref){
        try{
            LivraisonEntity livraison=livraisonComponent.getLivraisonByReference(ref);
            return adresseMapper.toResponse(livraisonComponent.getAdresseClientFromLivraison(livraison));
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }
    public Set<ProduitQuantiteResponseDTO> getProduitsGrpByQtt(String reference) throws Exception {
        Set<CommandeComponent.ProduitQuantite> totalProd = livraisonComponent.getProduitsGrpdByQuantité(reference);

        return totalProd.stream()
                .map(prodQuant -> ProduitQuantiteResponseDTO.builder()
                        .produit(produitMapper.toResponse(prodQuant.getProduit()))
                        .quantite(prodQuant.getQuantite())
                        .build())
                .collect(Collectors.toSet());


    }

    public LivraisonResponseDTO updateEtat(String reference, String nvEtat){
        try{
            return livraisonMapper.toResponse(livraisonComponent.updateEtat(reference, nvEtat));
        }catch (NotFoundLivraisonEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }


    public LivraisonResponseDTO updateHeureEff(String reference, Time heure){
        try{
            return livraisonMapper.toResponse(livraisonComponent.updtateHeureEff(reference,heure));
        }catch(NotFoundLivraisonEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
    public LivraisonResponseDTO updateTdmEff(String reference, Integer tdm){
        try{
            return livraisonMapper.toResponse(livraisonComponent.updtateTDMEff(reference,tdm));
        }catch(NotFoundLivraisonEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }



}