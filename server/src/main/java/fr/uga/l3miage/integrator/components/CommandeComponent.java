package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandeComponent {
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;

    public Set<CommandeEntity> getCommandeByReference(String reference) {
        return commandeRepository.findCommandeEntitiesByReference(reference);
    }

    public Set<CommandeEntity> getAllCommandeByLivraison(LivraisonEntity L){
        return commandeRepository.findCommandeEntitiesByLivraison(L);
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


}
