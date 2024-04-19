package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.CommandeComponent;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.models.CommandeEntity;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeComponent commandeComponent;
    public Adresse findClientAdressByCommande(CommandeEntity commande){
        return commandeComponent.findClientAdressByCommande(commande);
    }

    public Set<CommandeEntity> getAllCommandeByLivraison(LivraisonEntity L){
        return commandeComponent.getAllCommandeByLivraison(L);
    }
    public ClientEntity findByCommandesReference(CommandeEntity commande){
        ClientEntity cl=commandeComponent.findByCommandesReference(commande);
        return cl;
    }
    public Set<CommandeEntity> getCommandeByReference(String reference) {
        return commandeComponent.getCommandeByReference(reference);
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
}
