package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
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
    //private final ClientMapper clientMapper;
    public Adresse findClientAdressByCommande(CommandeEntity commande){

        return commandeComponent.findClientAdressByCommande(commande);
    }

    public Set<CommandeResponseDTO> getAllCommandeByLivraison(LivraisonEntity L) {
        try {
            Set<CommandeEntity> commandeEntities=commandeComponent.getAllCommandeByLivraison(L);
            Set<CommandeResponseDTO> commandeResponseDTOS = new HashSet<>();
            for (CommandeEntity commandeEntity : commandeEntities) {
                commandeResponseDTOS.add(commandeMapper.toResponseDTO(commandeEntity));
            }
            return commandeResponseDTOS;
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        }
/*
    public ClientResponseDTO findByCommandesReference(CommandeEntity commande){
        try {
            ClientEntity cl=commandeComponent.findByCommandesReference(commande);
            ClientResponseDTO client=clientMapper.toResponseDTO(cl);
            return client;
            }
         catch(Exception e){
            throw new RuntimeException();
         }


    }*/


    public CommandeResponseDTO getCommandeByReference(String reference) {
        try{
            CommandeEntity commande= commandeComponent.getCommandeByReference(reference);
            CommandeResponseDTO commandeResponseDTO=commandeMapper.toResponseDTO(commande);
            return commandeResponseDTO;
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
    public Set<CommandeResponseDTO> getAllCommandes() {
        System.out.println("qsfsdfsdfsdf");
        try {
            List<CommandeEntity> commandeEntities = commandeComponent.getAllCommandes();
            Set<CommandeResponseDTO> commandeResponseDTOS = new HashSet<>();
            for (CommandeEntity commandeEntity : commandeEntities) {
                commandeResponseDTOS.add(commandeMapper.toResponseDTO(commandeEntity));
            }
            return commandeResponseDTOS;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Map<String,Set<CommandeResponseDTO>> getCommandesGroupedByClient(){
        try{
            Map<String, List<CommandeEntity>> commandesGroupedByClient = commandeComponent.getCommandesGroupedByClient();

            // Utilisez le mapper pour mapper les commandes vers des DTO
            Map<String, Set<CommandeResponseDTO>> commandesDTOGroupedByClient = commandesGroupedByClient.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey, // Clé : nom du client
                            entry -> entry.getValue().stream() // Valeur : ensemble de CommandeResponseDTO
                                    .map(commandeMapper::toResponseDTO)
                                    .collect(Collectors.toSet())
                    ));

            return commandesDTOGroupedByClient;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    }



    /* VERSIONSANS COMPONENT
    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;
    public Set<CommandeEntity> getCommandeByReference(String reference) {
        return commandeRepository.findCommandeEntitiesByReference(reference);
    } done

    public Set<CommandeEntity> getAllCommandeByLivraison(LivraisonEntity L){
        return commandeRepository.findCommandeEntitiesByLivraison(L);
    } done
    public ClientEntity findByCommandesReference(CommandeEntity commande){
        ClientEntity cl=clientRepository.findClientEntityByCommandes(commande);
        //ClientEntity c=cl.stream().findFirst().orElse(null);
        return cl;
    } done
    public Adresse findClientAdressByCommande(CommandeEntity commande) {
        ClientEntity client = findByCommandesReference(commande);
        if (client != null) {
            return client.getAdresse();
        } else {
            // Gérer le cas où la commande n'est pas associée à un client
            return null;
        }
    }

    public void setCommandeRepository(CommandeRepository commandeRepository) {
        this.commandeRepository=commandeRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }*/

