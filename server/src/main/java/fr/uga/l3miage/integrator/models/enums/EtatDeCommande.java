package fr.uga.l3miage.integrator.models.enums;

public enum EtatDeCommande {
    ouverte,
    planifiee,
    enLivraison,
    livree,
    notee;
    public static EtatDeCommande parseStringToEtat(String str) {
        for (EtatDeCommande etat : EtatDeCommande.values()) {
            if (etat.name().equalsIgnoreCase(str)) {
                return etat;
            }
        }
        // Si aucun état correspondant n'est trouvé, vous pouvez renvoyer null ou lever une exception
        throw new IllegalArgumentException("L'état fourni n'est pas valide : " + str);
    }


    }
