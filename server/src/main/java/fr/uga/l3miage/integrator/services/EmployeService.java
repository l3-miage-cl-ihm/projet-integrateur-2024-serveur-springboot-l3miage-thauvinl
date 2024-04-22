package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeService {

    private final EmployeComponent employeComponent;
    private final EmployeRepository employeRepository;

    @Autowired
    public EmployeService(EmployeComponent employeComponent, EmployeRepository employeRepository) {
        this.employeComponent = employeComponent;
        this.employeRepository = employeRepository;
    }

    public Set<EmployeEntity> getLivreursByTourneeId(String tourneeId) throws NotFoundException {
        return employeComponent.getLivreursByTourneeId(tourneeId);
    }

    public List<EmployeEntity> getAllEmployes() {
        return employeRepository.findAll();
    }

    public EmployeEntity getEmployeById(String id) {
        Optional<EmployeEntity> optionalEmploye = employeRepository.findById(id);
        return optionalEmploye.orElse(null);
    }

    public EmployeEntity createEmploye(EmployeEntity employe) {
        return employeRepository.save(employe);
    }

    public EmployeEntity updateEmploye(String id, EmployeEntity employe) {
        Optional<EmployeEntity> optionalEmploye = employeRepository.findById(id);
        if (optionalEmploye.isPresent()) {
            EmployeEntity existingEmploye = optionalEmploye.get();
            existingEmploye.setEmail(employe.getEmail());
            existingEmploye.setNom(employe.getNom());
            existingEmploye.setPrenom(employe.getPrenom());
            existingEmploye.setTelephone(employe.getTelephone());
            existingEmploye.setEmploi(employe.getEmploi());
            // Set other fields as needed
            return employeRepository.save(existingEmploye);
        } else {
            return null; // Or handle the case where employe with given id is not found
        }
    }

    public void deleteEmploye(String id) {
        employeRepository.deleteById(id);
    }
}