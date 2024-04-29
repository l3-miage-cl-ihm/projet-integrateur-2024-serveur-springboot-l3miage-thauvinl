package fr.uga.l3miage.integrator.models.enums;

public class EtatDeCommandeClass {
    public enum EtatDeCommande{
        ouverte,
        planifiee,
        enLivraison,
        livree,
        notee
    }

    public enum EtatsDeTournee {
        planifiee,
        enChargement,
        enParcours,
        enDechargement,
        enClientele,
        enMontage,
        enRetour,
        effectuee
    }

    public enum EtatDeClient {
        livrable,
        aLivrer,
        livre
    }
}
