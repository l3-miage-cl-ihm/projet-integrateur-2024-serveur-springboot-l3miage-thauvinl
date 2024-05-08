package fr.uga.l3miage.integrator.controllers;
import fr.uga.l3miage.integrator.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ClientController{

    private final ClientService clientService;

}