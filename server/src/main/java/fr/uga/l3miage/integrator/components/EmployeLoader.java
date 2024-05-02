package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.buf.Ascii.toLower;

@Component
@AllArgsConstructor
public class EmployeLoader implements CommandLineRunner {

    private final EmployeRepository employeRepository;

    @Override
    public void run(String... args) throws Exception{
        Path path = Path.of("server/src/main/resources/Employés.csv");
        List<String> lines = Files.readAllLines(path);
        List<EmployeEntity> employes = lines.stream().skip(1) // Skip header line
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
    }
}
