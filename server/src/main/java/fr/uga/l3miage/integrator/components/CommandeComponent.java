package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommandeComponent {
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;

    public CommandeEntity getCommandeByReference(String reference) {
        return commandeRepository.findCommandeEntityByReference(reference);
    }

    public Set<CommandeEntity> getAllCommandeByLivraison(LivraisonEntity L){
        return commandeRepository.findCommandeEntitiesByLivraison(L);
    }
    public ClientEntity findByCommandesReference(CommandeEntity commande){
        ClientEntity cl=clientRepository.findClientEntityByCommandes(commande);
        return cl;
    }
    public Adresse findClientAdressByCommande(CommandeEntity commande) {
        ClientEntity client = findByCommandesReference(commande);
        if (client != null) {
            return client.getAdresse();
        } else {

            return null;
        }
    }
    public List<CommandeEntity> getAllCommandes(){
        return commandeRepository.findAll();
    }
    private Adresse getClientAdresse(CommandeEntity commande){
        ClientEntity client = findByCommandesReference(commande);
        return client.getAdresse();
    }
    public Map<Adresse, List<CommandeEntity>> getCommandesGroupedByClient() {
        List<CommandeEntity> commandes = commandeRepository.findAll();
        Map<Adresse, List<CommandeEntity>> commandesGroupedByClient = commandes.stream()
                .collect(Collectors.groupingBy(this::getClientAdresse));

        return commandesGroupedByClient;
    }
}
