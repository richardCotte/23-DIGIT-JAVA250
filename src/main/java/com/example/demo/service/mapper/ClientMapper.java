package com.example.demo.service.mapper;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClientMapper {

    public ClientDto clientDto(Client client) {
        return new ClientDto(client.getId(),
                client.getNom(),
                client.getPrenom(),
                client.getAge(LocalDate.now()));
    }

}
