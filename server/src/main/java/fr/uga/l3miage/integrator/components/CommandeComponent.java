package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.*;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import fr.uga.l3miage.integrator.dataType.Adresse;

import java.util.*;
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
    public Map<ProduitEntity, Integer> getProduitsGroupedByQtt(Set<CommandeEntity> commandes){
        Map<ProduitEntity,Integer> totalProduits = new HashMap<>();

        for (CommandeEntity commande : commandes) {
            for (LigneEntity ligne : commande.getLignesCommandes()) {
                ProduitEntity produit = ligne.getProduit();
                Integer quantiteLigne = ligne.getQuantite();



                if (totalProduits.containsKey(produit)) {

                    Iterator<Map.Entry<ProduitEntity, Integer>> iterator = totalProduits.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<ProduitEntity, Integer> entry = iterator.next();
                        if (entry.getKey().equals(produit)) {
                            Integer nouvelleQuantite = entry.getValue() + quantiteLigne;

                            entry.setValue(nouvelleQuantite);
                        }
                    }
                }
                    else {

                    totalProduits.put(produit,quantiteLigne);
                }

            }
        }

        return totalProduits;

    }

    public CommandeEntity updateEtat(String reference,String Etat){
        CommandeEntity commande=commandeRepository.findCommandeEntityByReference(reference);
        EtatDeCommande etat= EtatDeCommande.parseStringToEtat(Etat);
        commande.setEtat(etat);
        return commande;
    }
}
