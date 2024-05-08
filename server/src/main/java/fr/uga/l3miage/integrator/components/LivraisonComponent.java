package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class LivraisonComponent {
    private final LivraisonRepository livraisonRepository;
    private final CommandeComponent commandeComponent;
    public List<LivraisonEntity> getAllLivraison() {
        return livraisonRepository.findAll();
    }

    public LivraisonEntity getLivraisonByReference(String reference)  throws NotFoundLivraisonEntityException{
        return livraisonRepository.findLivraisonEntityByReference(reference).orElseThrow(()-> new NotFoundLivraisonEntityException(String.format("La livraison de référence %s n'a pas été trouvée", reference)));
    }
    public long countElementsInRepo(){
        return livraisonRepository.count();
    }

    public LivraisonEntity save(LivraisonEntity livraison){
        return livraisonRepository.save(livraison);
    }


    public Adresse getAdresseClientFromLivraison(LivraisonEntity livraisonEntity) throws NotFoundClientEntityExeption {
        Set<CommandeEntity> commandes= livraisonEntity.getCommandes();
        CommandeEntity cmTmp=commandes.stream().findFirst().orElse(null);
        return commandeComponent.findClientAdressByCommande(cmTmp);
    }

    public Set<CommandeComponent.ProduitQuantite> getProduitsGrpdByQuantité(String ref) throws Exception {
        try {
            Optional<LivraisonEntity> livraison = livraisonRepository.findLivraisonEntityByReference(ref);

            if (livraison.isPresent()) {
                return commandeComponent.getProduitsGroupedByQtt(livraison.get().getCommandes());
            } else {

                return Collections.emptySet();
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des produits groupés par quantité", e);
        }
    }

    public LivraisonEntity updateEtat(String reference, String nvEtat) throws NotFoundLivraisonEntityException{
        LivraisonEntity livraison = livraisonRepository.findLivraisonEntityByReference(reference).orElseThrow(()-> new NotFoundLivraisonEntityException(String.format("La livraison de référence %s n'a pas été trouvée", reference)));
        livraison.setEtat(EtatDeLivraison.valueOf(nvEtat));
        return livraisonRepository.save(livraison);
    }
}
