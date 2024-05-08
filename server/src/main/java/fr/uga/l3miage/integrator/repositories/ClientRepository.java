package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    ClientEntity findClientEntityByCommandes(CommandeEntity commande);
    ClientEntity findClientEntityByEmail(String email);
}
