package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeService {

    private final EmployeComponent employeComponent;

    @Autowired
    public EmployeService(EmployeComponent employeComponent) {
        this.employeComponent = employeComponent;
    }

    public Set<EmployeEntity> getLivreursByTourneeId(String tourneeId) throws NotFoundException {
        return employeComponent.getLivreursByTourneeId(tourneeId);
    }

    public List<EmployeEntity> getAllEmployes() {
        return employeComponent.getAllEmployes();
    }

    public EmployeEntity getEmployeById(String id) {
        Optional<EmployeEntity> optionalEmploye = employeComponent.getEmployeById(id);
        return optionalEmploye.orElse(null);
    }

    public EmployeEntity createEmploye(EmployeEntity employe) {
        return employeComponent.createEmploye(employe);
    }

    public EmployeEntity updateEmploye(String id, EmployeEntity employe) {
        return employeComponent.updateEmploye(id, employe);
    }

    public void deleteEmploye(String id) {
        employeComponent.deleteEmploye(id);
    }

    public Set<EmployeEntity> getAllLivreurs(){
        return employeComponent.getAllLivreurs();
    }
}
