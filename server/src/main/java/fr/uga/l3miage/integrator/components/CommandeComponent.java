package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.models.*;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import fr.uga.l3miage.integrator.dataType.Adresse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommandeComponent {
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;

    public CommandeEntity getCommandeByReference(String reference) throws NotFoundCommandeEntityException{
        return commandeRepository.findCommandeEntityByReference(reference).orElseThrow(() -> new NotFoundCommandeEntityException(String.format("La commande de référence %s est introuvable", reference)));
    }

    public Set<CommandeEntity> getAllCommandeByLivraison(LivraisonEntity L){
        return commandeRepository.findCommandeEntitiesByLivraison(L);
    }
    public ClientEntity findByCommandesReference(CommandeEntity commande) throws NotFoundClientEntityException {
        ClientEntity cl=clientRepository.findClientEntityByCommandes(commande).orElseThrow(()-> new NotFoundClientEntityException(String.format("Le clienr dont la commande est %s est introuvable",commande)));;
        return cl;
    }
    public Adresse findClientAdressByCommande(CommandeEntity commande) throws NotFoundClientEntityException {
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
    private Adresse getClientAdresse(CommandeEntity commande) throws NotFoundClientEntityException {
        ClientEntity client = findByCommandesReference(commande);
        return client.getAdresse();
    }
    public Map<Adresse, List<CommandeEntity>> getCommandesGroupedByClient(){

        List<CommandeEntity> commandes = commandeRepository.findAll();
        return commandes.stream()
                .collect(Collectors.groupingBy(com -> {
                    try {
                        return getClientAdresse(com);
                    } catch (NotFoundClientEntityException e) {
                        throw new RuntimeException(e);
                    }
                }));


    }





    public Set<ProduitQuantite> getProduitsGroupedByQtt(Set<CommandeEntity> commandes) {
        Set<ProduitQuantite> totalProduits = new HashSet<>();

        for (CommandeEntity commande : commandes) {
            for (LigneEntity ligne : commande.getLignesCommandes()) {
                ProduitEntity produit = ligne.getProduit();
                int quantiteLigne = ligne.getQuantite();

                boolean produitExist = false;

                // Vérifier si le produit existe déjà dans l'ensemble
                for (ProduitQuantite pq : totalProduits) {
                    if (pq.getProduit().equals(produit)) {
                        // Mettre à jour la quantité
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

    public CommandeEntity updateEtat(String reference,String Etat) throws NotFoundCommandeEntityException{
        CommandeEntity commande=commandeRepository.findCommandeEntityByReference(reference).orElseThrow(() -> new NotFoundCommandeEntityException(String.format("La commande de référence %s est introuvable", reference)));
        EtatDeCommande etat= EtatDeCommande.parseStringToEtat(Etat);
        commande.setEtat(etat);
        return commandeRepository.save(commande);
    }

    public CommandeEntity updateDateDeLivraison(String reference, String date) throws NotFoundCommandeEntityException {
        CommandeEntity commande = commandeRepository.findCommandeEntityByReference(reference).orElseThrow(() -> new NotFoundCommandeEntityException(String.format("La commande de référence %s est introuvable", reference)));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateDeLivraisonEffective = LocalDateTime.parse(date,formatter);
        commande.setDateDeLivraisonEffective(dateDeLivraisonEffective);
        commande.setDureeDeLivraison((int)ChronoUnit.DAYS.between(commande.getDateDeCreation(),commande.getDateDeLivraisonEffective()));
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