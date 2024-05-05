package fr.uga.l3miage.integrator.repositories;


import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
public interface EmployeRepository extends JpaRepository<EmployeEntity, String>{
    Set<EmployeEntity> findAllByEmploi(Emploi emploi);
    Optional<EmployeEntity> findByTrigramme(String trigramme);
}
