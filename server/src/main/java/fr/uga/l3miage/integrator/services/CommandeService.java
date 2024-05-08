package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import lombok.RequiredArgsConstructor;
import fr.uga.l3miage.integrator.mappers.CommandeMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

        } catch (Exception e){
            throw new RuntimeException();
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
            return retrieveCommandesGroupedByClient();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving commandes grouped by client: " + e.getMessage(), e);
        }
    }

    private Set<ClientCommandesPairResponseDTO> retrieveCommandesGroupedByClient() {
        try {
            Set<CommandeComponent.ClientCommandesPair> commandesGroupedByClient = commandeComponent.getCommandesGroupedByClient();

            return commandesGroupedByClient.stream()
                    .map(pair -> new ClientCommandesPairResponseDTO(
                            adresseMapper.toResponse(pair.getAdresse()),
                            pair.getCommandes().stream()
                                    .map(commandeMapper::toResponse)
                                    .collect(Collectors.toSet())
                    ))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving commandes grouped by client: " + e.getMessage(), e);
        }
    }

    public CommandeResponseDTO updateEtat(String ref, String etat){
        return commandeMapper.toResponse(commandeComponent.updateEtat(ref, etat));
         }
    }

