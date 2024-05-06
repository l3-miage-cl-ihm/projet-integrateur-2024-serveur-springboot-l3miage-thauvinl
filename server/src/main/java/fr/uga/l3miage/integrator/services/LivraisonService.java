package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.mappers.ProduitMapper;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.ProduitResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LivraisonService {
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
        return livraisonMapper.toResponse(livraisonComponent.getLivraisonByReference(reference));
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
    public Map< ProduitResponseDTO,Integer> getProduitsGrpByQtt(String reference) throws Exception {
        Map<ProduitEntity, Integer> totalProd = livraisonComponent.getProduitsGrpdByQuantité(reference);

        Map<ProduitResponseDTO,Integer> result = totalProd.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> produitMapper.toResponse(entry.getKey()),
                        Map.Entry::getValue
                ));

        return result;
    }


}