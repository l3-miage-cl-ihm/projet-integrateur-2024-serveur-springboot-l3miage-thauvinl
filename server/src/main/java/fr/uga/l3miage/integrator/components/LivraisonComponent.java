package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import fr.uga.l3miage.integrator.services.CommandeService;
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

    public LivraisonEntity getLivraisonByReference(String reference) {
        return livraisonRepository.findLivraisonEntityByReference(reference);
    }

    public long countElementsInRepo() {
        return livraisonRepository.count();
    }


    public Adresse getAdresseClientFromLivraison(LivraisonEntity livraisonEntity) {
        Set<CommandeEntity> commandes = livraisonEntity.getCommandes();
        CommandeEntity cm_tmp = commandes.stream().findFirst().orElse(null);
        return commandeComponent.findClientAdressByCommande(cm_tmp);
    }


}
