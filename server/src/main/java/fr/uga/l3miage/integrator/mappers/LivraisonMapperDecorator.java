package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LivraisonMapperDecorator implements LivraisonMapper{
    @Autowired
    @Qualifier("delegate")
    private LivraisonMapper livraisonMapper;

    @Autowired
    private CommandeMapper commandeMapper;

    @Autowired
    private CommandeComponent commandeComponent;

    @Override
    public LivraisonEntity toEntity(LivraisonCreationRequest request) {
        LivraisonEntity livraisonEntity = livraisonMapper.toEntity(request);
        livraisonEntity.setCommandes(new HashSet<>());
        request.getRefCommande().forEach(ref -> {
            try {
                CommandeEntity commande = commandeComponent.getCommandeByReference(ref);
                livraisonEntity.addCommandesInLivraison(commande);
            } catch (NotFoundCommandeEntityException e) {
                throw new NotFoundEntityRestException(e.getMessage());
            }
        });
        return livraisonEntity;
    }
}
