package fr.uga.l3miage.integrator.controllers;


import fr.uga.l3miage.integrator.endpoints.LivraisonEndpoints;
import fr.uga.l3miage.integrator.responses.*;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.Set;

@RestController

public class LivraisonController implements LivraisonEndpoints{

    @Autowired
    private LivraisonService livraisonService;


    @Override
    public List<LivraisonResponseDTO> getAllLivraisons() {
        return livraisonService.getAllLivraison();

    }
    @Override
    public LivraisonResponseDTO getLivraisonByReference( String reference) {
        return livraisonService.getLivraisonByReference(reference);

    }
    @Override
    public ResponseEntity<Long> countLivraisons() {
        long count = livraisonService.countElementsInRepo();
        return new ResponseEntity<>(count, HttpStatus.OK);}

    @Override
    public AdresseResponseDTO getAdresseClientFromLivraison(String reference){
        return livraisonService.getAdresseClientFromLivraison(reference);
    }

    @Override
    public Set<ProduitQuantiteResponseDTO> getProduitsGrpedByQtt(String reference)  {

        return livraisonService.getProduitsGrpByQtt(reference);
    }

    @Override
    public LivraisonResponseDTO updateEtat(String reference, String nvEtat) {
        return livraisonService.updateEtat(reference, nvEtat);
    }

    @Override
    public LivraisonResponseDTO updateHeure(String reference, Time heure) {
        return livraisonService.updateHeureEff(reference,heure);
    }

    @Override
    public LivraisonResponseDTO updateTdmEff(String reference, Integer tdm) {
        return livraisonService.updateTdmEff(reference,tdm);
    }

}
