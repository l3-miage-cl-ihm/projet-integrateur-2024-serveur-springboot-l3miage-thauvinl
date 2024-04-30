package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommandeClass;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
public class ClientLoader implements CommandLineRunner {

    private final ClientRepository clientRepository;


    @Override
    public void run(String... args) throws Exception{

        Path pathClient = Path.of("server/src/main/resources/Clients.csv");
        Path pathCommandes = Path.of("server/src/main/resources/Commandes_ouvertes.csv");
        Map<String, CommandeEntity> commandesDetails = new HashMap<>();

        try(Stream<String> stream = Files.lines(pathCommandes)) {
            stream.skip(1)
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
        }
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
