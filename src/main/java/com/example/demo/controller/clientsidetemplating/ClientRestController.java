package com.example.demo.controller.clientsidetemplating;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientDto> getClients() {
        return clientService.findAll();
    }
}
