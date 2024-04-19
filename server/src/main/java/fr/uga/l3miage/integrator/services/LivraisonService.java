package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.List;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;

@Service
@RequiredArgsConstructor
public class LivraisonService {
    private final LivraisonComponent livraisonComponent;

    
    public List<LivraisonEntity> getAllLivraison() {
        return livraisonComponent.getAllLivraison();
    }

    public LivraisonEntity getLivraisonByReference(String reference) {
        return livraisonComponent.getLivraisonByReference(reference);
    }
    public long countElementsInRepo(){
        return livraisonComponent.countElementsInRepo();
    }

}
