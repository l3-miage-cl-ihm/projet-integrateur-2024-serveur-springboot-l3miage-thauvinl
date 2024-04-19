package fr.uga.l3miage.integrator.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/livraisons")
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ObjectMapper objectMapper; //object pour mapper valeur envoyé par API angular



    @GetMapping("AllLivraisons")
    public ResponseEntity<List<LivraisonEntity>> getAllLivraisons() {
        List<LivraisonEntity> livraisons = livraisonService.getAllLivraison();
        return new ResponseEntity<>(livraisons, HttpStatus.OK);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<LivraisonEntity> getLivraisonByReference(@PathVariable String reference) {
        LivraisonEntity livraison = livraisonService.getLivraisonByReference(reference);
        if (livraison == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livraison, HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> countLivraisons() {
        long count = livraisonService.countElementsInRepo();
        return new ResponseEntity<>(count, HttpStatus.OK);}

    @PostMapping("/adresseFromLivraison")
    public ResponseEntity<Adresse> getAdresseClientFromLivraison(@RequestBody String jsonData) throws JsonProcessingException {

        String id_livraison=objectMapper.writeValueAsString(jsonData); //mapper jsonData en un string qui est la ref
        LivraisonEntity livraisonEntity=livraisonService.getLivraisonByReference(id_livraison);
        if (livraisonEntity== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<CommandeEntity> commandeEntitySet=commandeService.getAllCommandeByLivraison(livraisonEntity);
        CommandeEntity cm_tmp=commandeEntitySet.stream().findFirst().orElse(null);
        if (cm_tmp == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Adresse add = commandeService.findClientAdressByCommande(cm_tmp);
        return new ResponseEntity<>(add, HttpStatus.OK);

    }

}
