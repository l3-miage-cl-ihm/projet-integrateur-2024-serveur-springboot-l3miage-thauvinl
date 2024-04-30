package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.repositories.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProduitLoader implements CommandLineRunner {


    private final ProduitRepository produitRepository;

    @Override
    public void run(String... args) throws Exception{
        Path path = Path.of("server/src/main/resources/Produits.csv");
        List<String> lines = Files.readAllLines(path);
        List<ProduitEntity> produits = lines.stream().skip(1) // Skip header line
                .map(line -> line.split(";"))
                .map(data -> {
                    ProduitEntity p = new ProduitEntity();
                    p.setId(data[0]);
                    p.setTitre(data[1]);
                    p.setDescription(data[2]);
                    p.setTempsDeMontageTheorique(data[3].isEmpty() ? null : Integer.parseInt(data[3]));
                    p.setOptionDeMontage(Boolean.parseBoolean(data[4]));
                    p.setPrix(Double.parseDouble(data[5].replace("€", "").trim()));
                    return p;
                })
                .collect(Collectors.toList());

        produitRepository.saveAll(produits);
    }
}
