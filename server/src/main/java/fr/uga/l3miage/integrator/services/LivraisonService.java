package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.List;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;

@Service
public class LivraisonService {
    @Autowired
    private LivraisonRepository livraisonRepository;

    
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
