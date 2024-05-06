package fr.uga.l3miage.integrator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.endpoints.LivraisonEndpoints;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.services.CommandeService;
import fr.uga.l3miage.integrator.services.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;

@RestController

public class LivraisonController implements LivraisonEndpoints{

    @Autowired
    private LivraisonService livraisonService;


    //plugin sonarelint
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

}
