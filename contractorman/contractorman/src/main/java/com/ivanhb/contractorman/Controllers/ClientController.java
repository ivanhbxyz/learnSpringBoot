package com.ivanhb.contractorman.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivanhb.contractorman.Repositories.ClientRepository;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @GetMapping
    public List<Client> getClients
    
}
