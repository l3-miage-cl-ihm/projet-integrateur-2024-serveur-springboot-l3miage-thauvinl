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

public abstract class LivraisonController implements LivraisonEndpoints{

    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ObjectMapper objectMapper; //object pour mapper valeur envoyé par API angular


    //plugin sonarelint
    @Override
    public List<LivraisonResponseDTO> getAllLivraisons() {
        return livraisonService.getAllLivraison();

    }


    public LivraisonResponseDTO getLivraisonByReference(@PathVariable String reference) {
        return livraisonService.getLivraisonByReference(reference);

    }



    public ResponseEntity<Long> countLivraisons() {
        long count = livraisonService.countElementsInRepo();
        return new ResponseEntity<>(count, HttpStatus.OK);}

    @Override
    public AdresseResponseDTO getAdresseClientFromLivraison(String jsonData) throws JsonProcessingException {
        return livraisonService.getAdresseClientFromLivraison(jsonData);
    }/*
    public LivraisonResponseDTO addCmdInLivraison(String refL, String refC){
        return livraisonService.addCommandeInLivraison(refL,refC);

    }*/

/*
    @PostMapping("/adresseFromLivraison")
    public ResponseEntity<Adresse> getAdresseClientFromLivraison(@RequestBody String jsonData) throws JsonProcessingException {

        String id_livraison=objectMapper.writeValueAsString(jsonData); //mapper jsonData en un string qui est la ref
        LivraisonEntity livraisonEntity=livraisonService.getLivraisonByReference(id_livraison);
        if (livraisonEntity== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<CommandeResponseDTO> commandeEntitySet=commandeService.getAllCommandeByLivraison(livraisonEntity);
        CommandeEntity cm_tmp=commandeEntitySet.stream().findFirst().orElse(null);
        if (cm_tmp == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Adresse add = commandeService.findClientAdressByCommande(cm_tmp);
        return new ResponseEntity<>(add, HttpStatus.OK);

    }*/

}
