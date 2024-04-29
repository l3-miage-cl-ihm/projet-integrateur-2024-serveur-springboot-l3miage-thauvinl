package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProduitLoader implements CommandLineRunner {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public void run(String... args) throws Exception{
        if (produitRepository.count() == 0) { // Vérifier si la base est vide
            Path csvPath = Path.of("C:\\Users\\user\\OneDrive\\Bureau\\projet-integrateur-2024-serveur-springboot-l3miage-thauvinl\\server\\src\\main\\resources\\Export_Produits_netoye.csv");
            List<ProduitEntity> produits = new ArrayList<>();

            List<String> lines = Files.readAllLines(csvPath);


            for (String line : lines.stream().skip(1).collect(Collectors.toList())) {
                String[] data = line.split(";");

                if (data.length == 6) {
                    ProduitEntity p = new ProduitEntity();
                    p.setId(data[0].trim());
                    p.setDescription(data[1].trim());
                    p.setOptionDeMontage(Boolean.parseBoolean(data[2].trim()));
                    p.setPrix(Double.parseDouble(data[3].trim()));
                    p.setTempsDeMontageTheorique(Integer.parseInt(data[4].trim()));
                    p.setTitre(data[5].trim());
                    produitRepository.save(p); // Enregistrer le produit
                } else {
                    System.err.println("Ligne ignorée pour nombre de colonnes incorrect : " + line);
                }
            }
        }


    }
}
