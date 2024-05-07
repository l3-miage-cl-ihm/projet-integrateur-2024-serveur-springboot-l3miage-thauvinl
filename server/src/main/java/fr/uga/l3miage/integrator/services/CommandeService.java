package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.mappers.AdresseMapper;
import fr.uga.l3miage.integrator.mappers.ClientMapper;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
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
            CommandeResponseDTO commandeResponseDTO=commandeMapper.toResponse(commande);
            return commandeResponseDTO;
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

    public Set<ClientCommandesPairResponseDTO> getCommandesGroupedByClient(){
        try{
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
                throw new RuntimeException("commande service cmdGrpedByclient 1"+e);
            }
        }catch (Exception e){
            throw new RuntimeException("commande service cmdGrpedByclient 2"+e);
        }
    }

    public CommandeResponseDTO updateEtat(String ref, String etat){
        return commandeMapper.toResponse(commandeComponent.updateEtat(ref, etat));
         }
    }

