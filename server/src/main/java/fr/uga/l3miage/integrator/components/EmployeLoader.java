package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeLoader implements CommandLineRunner {
    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public void run(String... args) throws Exception{
        Path path = Path.of("server/src/main/resources/Export_Employés_netoye.csv");
        List<String> lines = Files.readAllLines(path);
        List<EmployeEntity> employes = lines.stream().skip(1) // Skip header line
                .map(line -> line.split(","))
                .map(data -> {
                    EmployeEntity employe = new EmployeEntity();
                    employe.setTrigramme(data[0]);
                    employe.setEmploi(data[1].isEmpty() ? null : Emploi.valueOf(data[1]));
                    employe.setNom(data[2]);
                    employe.setPrenom(data[3]);
                    employe.setTelephone(data[4]);
                    employe.setEmail(data.length>5 ? data[5] : null);
                    return employe;
                })
                .collect(Collectors.toList());

        employeRepository.saveAll(employes);
    }
}
