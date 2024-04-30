package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommandeClass;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
/*@Component
public class CommandeLoader implements CommandLineRunner {
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public void run(String... args) throws Exception{
        Path path = Path.of("server/src/main/resources/commandes_ouvertes_netoye.csv");
        List<String> lines = Files.readAllLines(path);
        List<CommandeEntity> commandes = lines.stream().skip(1) // Skip header line
                .map(line -> line.split(","))
                .map(data -> {
                    CommandeEntity commande = new CommandeEntity();
                    commande.setReference(data[0]);
                    commande.setEtat(data[1].isEmpty() ? null : EtatDeCommandeClass.EtatDeCommande.valueOf(data[1]));
                    commande.setDateDeCreation(Date.valueOf(data[2]));
                    commande.setNote(data[3].isEmpty() ? null : Integer.parseInt(data[3]));
                    commande.setCommentaire(data[4].isEmpty() ? null : data[4]);
                    commande.setDateDeLivraisonEffective(data[5].isEmpty() ? null : Date.valueOf(data[5]));
                    commande.setDureeDeLivraison((data[6].isEmpty() ? null : Integer.parseInt(data[6])));
                    commande.setTddTheorique(data[7].isEmpty() ? null : Integer.parseInt(data[7]));
                    commande.setTdmTheorique(data[8].isEmpty() ? null : Integer.parseInt(data[8]));
                    commande.setMontant(data[9].isEmpty() ? null : Double.parseDouble(data[9]));
                    return commande;
                })
                .collect(Collectors.toList());

        commandeRepository.saveAll(commandes);
    }
}*/
