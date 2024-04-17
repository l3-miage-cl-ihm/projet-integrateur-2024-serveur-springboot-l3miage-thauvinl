package fr.uga.l3miage.integrator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import java.util.List;

@RestController
@RequestMapping("/livraisons")
public class LivraisonController {

    @Autowired
    private LivraisonRepository livraisonRepository;

    @GetMapping
    public ResponseEntity<List<LivraisonEntity>> getAllLivraisons() {
        List<LivraisonEntity> livraisons = livraisonRepository.findAll();
        return new ResponseEntity<>(livraisons, HttpStatus.OK);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<LivraisonEntity> getLivraisonByReference(@PathVariable String reference) {
        LivraisonEntity livraison = livraisonRepository.findById(reference).orElse(null);
        if (livraison == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livraison, HttpStatus.OK);
    }

    // Ajoutez ici d'autres méthodes pour gérer les opérations CRUD des livraisons
}
