package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
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
    public ClientEntity findByCommandesReference(CommandeEntity commande) throws NotFoundClientEntityExeption {
        ClientEntity cl=clientRepository.findClientEntityByCommandes(commande).orElseThrow(()-> new NotFoundClientEntityExeption(String.format("Le clienr dont la commande est %s est introuvable",commande)));;
        return cl;
    }
    public Adresse findClientAdressByCommande(CommandeEntity commande){
        try{ClientEntity client = findByCommandesReference(commande);
            return client.getAdresse();}
        catch (NotFoundClientEntityExeption e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
    public List<CommandeEntity> getAllCommandes(){
        return commandeRepository.findAll();
    }

    public Map<Adresse, List<CommandeEntity>> getCommandesGroupedByClient(){

        List<CommandeEntity> commandes = commandeRepository.findAll().stream().limit(30).collect(Collectors.toList());
        return commandes.stream()
                .collect(Collectors.groupingBy(com -> {
                    return findClientAdressByCommande(com);
                }));


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