package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import lombok.RequiredArgsConstructor;
import fr.uga.l3miage.integrator.mappers.CommandeMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeComponent commandeComponent;
    private final CommandeMapper commandeMapper;
    private final AdresseMapper adresseMapper;




    public CommandeResponseDTO getCommandeByReference(String reference) {
        try{
            CommandeEntity commande= commandeComponent.getCommandeByReference(reference);
            return commandeMapper.toResponse(commande);

        } catch (NotFoundCommandeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
    public Set<CommandeResponseDTO> getAllCommandes() {
        try {
            List<CommandeEntity> commandeEntities = commandeComponent.getAllCommandes();
            Set<CommandeResponseDTO> commandeResponseDTOS = new HashSet<>();
            for (CommandeEntity commandeEntity : commandeEntities) {
                commandeResponseDTOS.add(commandeMapper.toResponse(commandeEntity));
            }
            return commandeResponseDTOS;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Set<ClientCommandesPairResponseDTO> getCommandesGroupedByClient() {
        try {
            Map<Adresse, List<CommandeEntity>> commandesGroupedByClient = commandeComponent.getCommandesGroupedByClient();

            return commandesGroupedByClient.entrySet().stream()
                    .map(entry -> new ClientCommandesPairResponseDTO(
                            adresseMapper.toResponse(entry.getKey()),
                            entry.getValue().stream()
                                    .map(commandeMapper::toResponse)
                                    .collect(Collectors.toSet())
                    ))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes groupées par client", e);
        }
    }

    public CommandeResponseDTO updateEtat(String ref, String etat){
        try {
            return commandeMapper.toResponse(commandeComponent.updateEtat(ref, etat));
        }catch (NotFoundCommandeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

    public CommandeResponseDTO updateDateDeLivraison(String reference, String date){
        try{
            return commandeMapper.toResponse(commandeComponent.updateDateDeLivraison(reference, date));
        }catch (NotFoundCommandeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
}



