package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LivraisonComponent {
    private final LivraisonRepository livraisonRepository;


    public List<LivraisonEntity> getAllLivraison() {
        return livraisonRepository.findAll();
    }

    public LivraisonEntity getLivraisonByReference(String reference) {
        return livraisonRepository.findLivraisonEntityByReference(reference);
    }
    public long countElementsInRepo(){
        return livraisonRepository.count();
    }
}
