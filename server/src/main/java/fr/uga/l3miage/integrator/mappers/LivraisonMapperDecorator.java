package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.models.enums.EtatDeLivraison;
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
        livraisonEntity.setEtat(EtatDeLivraison.planifiee);
        livraisonEntity.setCommandes(new HashSet<>());
        request.getRefCommande().forEach(ref -> {
            try {
                CommandeEntity commande = commandeComponent.getCommandeByReference(ref);
                commande.setEtat(EtatDeCommande.planifiee);
                livraisonEntity.addCommandesInLivraison(commande);
            } catch (NotFoundCommandeEntityException e) {
                throw new NotFoundEntityRestException(e.getMessage());
            }
        });
        return livraisonEntity;
    }

    @Override
    public LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity) {

        LivraisonResponseDTO responseDTO =  livraisonMapper.toResponse(livraisonEntity);

        if (livraisonEntity.getCommandes() == null || livraisonEntity.getCommandes().isEmpty()) {


            responseDTO.setMontant(0.0);
            responseDTO.setTdmTheorique(0);
        } else {

            Set<CommandeResponseDTO> commandeResponseDTOS = livraisonEntity.getCommandes().stream()
                    .map(commandeMapper::toResponse)
                    .collect(Collectors.toSet());
            responseDTO.setCommandes(commandeResponseDTOS);
            responseDTO.setTdmTheorique(responseDTO.getCommandes().stream()
                    .mapToInt(CommandeResponseDTO::getTdmTheorique)
                    .sum());
            responseDTO.setMontant(responseDTO.getCommandes().stream()
                    .mapToDouble(CommandeResponseDTO::getMontant)
                    .sum());


        }
        return responseDTO;
    }
}
