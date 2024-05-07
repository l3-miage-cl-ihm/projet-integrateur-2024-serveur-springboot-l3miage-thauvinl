package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.*;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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
    public Set<ClientCommandesPair> getCommandesGroupedByClient() {
        List<CommandeEntity> commandes = commandeRepository.findAll();


        Set<ClientCommandesPair> result = commandes.stream()
                .collect(Collectors.groupingBy(this::getClientAdresse,
                        Collectors.toSet()))
                .entrySet().stream()
                .map(entry -> new ClientCommandesPair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());

        return result;
    }





    public Set<ProduitQuantite> getProduitsGroupedByQtt(Set<CommandeEntity> commandes) {
        Set<ProduitQuantite> totalProduits = new HashSet<>();

        for (CommandeEntity commande : commandes) {
            for (LigneEntity ligne : commande.getLignesCommandes()) {
                ProduitEntity produit = ligne.getProduit();
                int quantiteLigne = ligne.getQuantite();

                boolean produitExist = false;

                for (ProduitQuantite pq : totalProduits) {
                    if (pq.getProduit().equals(produit)) {

                        pq.quantite += quantiteLigne;
                        produitExist = true;
                        break;
                    }
                }

                // Si le produit n'existe pas encore, l'ajouter à l'ensemble
                if (!produitExist) {
                    totalProduits.add(new ProduitQuantite(produit, quantiteLigne));
                }
            }
        }

        return totalProduits;
    }

    public CommandeEntity updateEtat(String reference,String Etat){
        CommandeEntity commande=commandeRepository.findCommandeEntityByReference(reference);
        EtatDeCommande etat= EtatDeCommande.parseStringToEtat(Etat);
        commande.setEtat(etat);
        return commandeRepository.save(commande);
    }
    /***********************************CLASSES STATIC*************************/
    public static class ClientCommandesPair {
        private final Adresse adresse;
        private final Set<CommandeEntity> commandes;

        public ClientCommandesPair(Adresse adresse, Set<CommandeEntity> commandes) {
            this.adresse = adresse;
            this.commandes = commandes;
        }

        public Adresse getAdresse() {
            return adresse;
        }

        public Set<CommandeEntity> getCommandes() {
            return commandes;
        }
    }
    public static class ProduitQuantite {
        private ProduitEntity produit;
        private int quantite;

        public ProduitQuantite(ProduitEntity produit, int quantite) {
            this.produit = produit;
            this.quantite = quantite;
        }

        public ProduitEntity getProduit() {
            return produit;
        }

        public int getQuantite() {
            return quantite;
        }
    }

}
