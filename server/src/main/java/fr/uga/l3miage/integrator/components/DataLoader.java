package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.*;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.LigneRepository;
import fr.uga.l3miage.integrator.repositories.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final LigneRepository ligneRepository;
    private final EmployeRepository employeRepository;

    @Override
    public void run(String... args) throws Exception {
        Path pathClient = Path.of("server/src/main/resources/Clients.csv");
        Path pathCommandes = Path.of("server/src/main/resources/Commandes_ouvertes.csv");
        Path pathProduits = Path.of("server/src/main/resources/Produits.csv");
        Path pathLignes = Path.of("server/src/main/resources/Lignes.csv");
        Path pathEmployes = Path.of("server/src/main/resources/Employés.csv");

        Map<String, CommandeEntity> commandesDetails = new HashMap<>();

        try (Stream<String> stream = Files.lines(pathCommandes)) {
            stream.skip(1)
                    .forEach(line -> {
                        String[] dataCmd = line.split(",");
                        CommandeEntity commande = new CommandeEntity();
                        commande.setReference(dataCmd[0]);
                        commande.setEtat(EtatDeCommande.ouverte);
                        commande.setNote(Integer.parseInt(dataCmd[4]));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        commande.setDateDeCreation(LocalDateTime.parse(dataCmd[2], formatter));
                        commande.setMontant(Double.parseDouble(dataCmd[8]));
                        commande.setTdmTheorique(Integer.parseInt(dataCmd[7]));
                        commande.setDureeDeLivraison(Integer.parseInt(dataCmd[5]));
                        commandesDetails.put(dataCmd[0], commande);
                    });
        }

        // Parsing employes...
        List<String> linesEmployes = Files.readAllLines(pathEmployes);
        List<EmployeEntity> employes = linesEmployes.stream().skip(1) // Skip header line
                .map(line -> line.split(","))
                .map(data -> {
                    EmployeEntity employe = new EmployeEntity();
                    employe.setTrigramme(data[0]);
                    employe.setEmploi(data[1].isEmpty() ? null : Emploi.valueOf(data[1]));
                    employe.setNom(data[2]);
                    employe.setPrenom(data[3]);
                    employe.setEmail(employe.getNom().toLowerCase() +"." +employe.getPrenom()+"@ikeo.fr");
                    employe.setTelephone(data[4]);
                    return employe;
                })
                .collect(Collectors.toList());

        employeRepository.saveAll(employes);

        // Parsing clients...
        List<String> lines = Files.readAllLines(pathClient);
        List<ClientEntity> clients = lines.stream().skip(1)
                .map(line -> line.split(";"))
                .map(data -> {
                    ClientEntity client = new ClientEntity();
                    client.setEmail(data[0]);
                    client.setPrenom(data[1]);
                    client.setNom(data[2]);
                    client.setAdresse(Adresse.builder()
                            .adresse(data[3])
                            .codePostal(data[4])
                            .ville(data[5])
                            .build());
                    if (data.length > 6 && !data[6].isEmpty()) {
                        List<CommandeEntity> commandes = Arrays.stream(data[6].split(","))
                                .filter(commandesDetails::containsKey)
                                .map(commandesDetails::get)
                                .collect(Collectors.toList());
                        client.setCommandes(new HashSet<>(commandes));
                    }
                    return client;
                })
                .collect(Collectors.toList());
        clientRepository.saveAll(clients);

        // Parsing produits...
        List<String> productLines = Files.readAllLines(pathProduits);
        List<ProduitEntity> produits = productLines.stream().skip(1) // Skip header line
                .map(line -> line.split(";"))
                .map(data -> {
                    ProduitEntity p = new ProduitEntity();
                    p.setReference(data[0]);
                    p.setTitre(data[1]);
                    p.setDescription(data[2]);
                    p.setTempsDeMontageTheorique(data[3].isEmpty() ? null : Integer.parseInt(data[3]));
                    p.setOptionDeMontage(Boolean.parseBoolean(data[4]));
                    p.setPrix(Double.parseDouble(data[5].replace("€", "").trim()));
                    return p;
                })
                .collect(Collectors.toList());
        produitRepository.saveAll(produits);

        // Parsing lignes...
        try (Stream<String> ligneStream = Files.lines(pathLignes)) {
            ligneStream.skip(1) // Skip header
                    .forEach(line -> {
                        String[] data = line.split(",");
                        String commandeRef = data[1];
                        if (commandesDetails.containsKey(commandeRef)) {
                            LigneEntity ligne = new LigneEntity();
                            ligne.setReference(data[0]);
                            ligne.setCommande(commandesDetails.get(commandeRef));
                            ligne.setProduit(produitRepository.findById(data[2]).orElse(null));
                            ligne.setQuantite(Integer.parseInt(data[3]));
                            ligne.setMontant();
                            ligne.setOptionDeMontage(Boolean.parseBoolean(data[4]));
                            ligneRepository.save(ligne);
                        }
                    });
        }
    }
}
