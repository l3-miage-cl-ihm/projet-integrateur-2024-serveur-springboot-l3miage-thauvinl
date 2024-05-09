package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
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


    public Adresse getAdresseClientFromLivraison(LivraisonEntity livraisonEntity) throws NotFoundClientEntityException {
        Set<CommandeEntity> commandes= livraisonEntity.getCommandes();
        CommandeEntity cmTmp=commandes.stream().findFirst().orElse(null);
        return commandeComponent.findClientAdressByCommande(cmTmp);
    }

    public Set<CommandeComponent.ProduitQuantite> getProduitsGrpdByQuantité(String ref) throws NotFoundLivraisonEntityException {
        try {
            if (livraisonRepository.findLivraisonEntityByReference(ref).isPresent()) {
                return commandeComponent.getProduitsGroupedByQtt(
                        livraisonRepository.findLivraisonEntityByReference(ref).get().getCommandes()
                );
            } else {
                return Collections.emptySet();
            }
        } catch (Exception e) {
            throw new NotFoundLivraisonEntityException("Erreur lors de la récupération des produits groupés par quantité");
        }
    }



    public LivraisonEntity updateEtat(String reference, String nvEtat) throws NotFoundLivraisonEntityException{
        LivraisonEntity livraison = livraisonRepository.findLivraisonEntityByReference(reference).orElseThrow(()-> new NotFoundLivraisonEntityException(String.format("La livraison de référence %s n'a pas été trouvée", reference)));
        livraison.setEtat(EtatDeLivraison.valueOf(nvEtat));
        return livraisonRepository.save(livraison);
    }
    public LivraisonEntity updtateHeureEff(String reference, Time heure) throws NotFoundLivraisonEntityException{
        LivraisonEntity livraison = livraisonRepository.findLivraisonEntityByReference(reference).orElseThrow(()-> new NotFoundLivraisonEntityException(String.format("La livraison de référence %s n'a pas été trouvée", reference)));
        livraison.setHeureDeLivraisonEffective(heure);
        return livraisonRepository.save(livraison);
    }
    public LivraisonEntity updtateTDMEff(String reference, Integer tdm) throws NotFoundLivraisonEntityException{
        LivraisonEntity livraison = livraisonRepository.findLivraisonEntityByReference(reference).orElseThrow(()-> new NotFoundLivraisonEntityException(String.format("La livraison de référence %s n'a pas été trouvée", reference)));
        livraison.setTdmEffectif(tdm);
        return livraisonRepository.save(livraison);
    }
}
