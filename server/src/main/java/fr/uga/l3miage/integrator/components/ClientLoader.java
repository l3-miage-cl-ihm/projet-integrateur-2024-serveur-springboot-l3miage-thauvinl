package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommandeClass;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Component
public class ClientLoader implements CommandLineRunner {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public void run(String... args) throws Exception{

        Path pathClient = Path.of("server/src/main/resources/ClientsSansGeoLocModif.csv");
        Path pathCommandes = Path.of("server/src/main/resources/commandes_ouvertes.csv");
        Map<String, CommandeEntity> commandesDetails = new HashMap<>();

        Files.lines(pathCommandes)
                .skip(1)
                .forEach(line -> {
                    String[] dataCmd = line.split(",");
                    CommandeEntity commande = new CommandeEntity();
                    commande.setReference(dataCmd[0]);
                    commande.setEtat(EtatDeCommandeClass.EtatDeCommande.ouverte);
                    commande.setNote(Integer.parseInt(dataCmd[4]));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    commande.setDateDeCreation(LocalDateTime.parse(dataCmd[2], formatter));
                    commande.setMontant(Double.parseDouble(dataCmd[8]));
                    commande.setTdmTheorique(Integer.parseInt(dataCmd[7]));
                    commande.setDureeDeLivraison(Integer.parseInt(dataCmd[5]));
                    commandesDetails.put(dataCmd[0], commande);
                });
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
    }

}
