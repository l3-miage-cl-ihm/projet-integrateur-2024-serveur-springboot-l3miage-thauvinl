package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.models.CommandeEntity;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;
    public Set<CommandeEntity> getCommandeByReference(String reference) {
        return commandeRepository.findCommandeEntitiesByReference(reference);
    }

    public Set<CommandeEntity> getAllCommandeByLivraison(LivraisonEntity L){
        return commandeRepository.findCommandeEntitiesByLivraisonEntity(L);
    }
    public ClientEntity findByCommandesReference(CommandeEntity commande){
        ClientEntity cl=clientRepository.findClientEntityByCommandes(commande);
        //ClientEntity c=cl.stream().findFirst().orElse(null);
        return cl;
    }
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
    }
}
