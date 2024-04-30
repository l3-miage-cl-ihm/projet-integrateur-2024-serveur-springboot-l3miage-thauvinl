package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.dataType.GeoPosition;
import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.repositories.CamionRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CamionLoader implements CommandLineRunner {

    private final CamionRepository camionRepository;
    @Override
    public void run(String... args) throws Exception{
        Path path = Path.of("server/src/main/resources/Camions.csv");
        List<String> lines = Files.readAllLines(path);
        List<CamionEntity> camions = lines.stream().skip(1) // Skip header line
                .map(line -> line.split(","))
                .map(data -> {
                    CamionEntity camion = new CamionEntity();
                    camion.setImmatriculation(data[0]);
                    camion.setPosition(GeoPosition.builder()
                            .latitude(Double.parseDouble(data[1]))
                            .longitude(Double.parseDouble(data[2]))
                            .build());
                    return camion;
                })
                .collect(Collectors.toList());

        camionRepository.saveAll(camions);
    }
}
