package fr.uga.l3miage.integrator.controllers;


import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/{email}")
    public ResponseEntity<ClientEntity> getClientByEmail(@PathVariable String email) {
        ClientEntity client = clientService.getClientByEmail(email);
       if (client!=null){
           return new ResponseEntity<>(client, HttpStatus.OK);
       }
       else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
