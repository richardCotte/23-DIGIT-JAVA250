package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ExportClientXLSXServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ExportClientXLSXService exportClientXLSXService;

    @Test
    public void generateXLSX() throws IOException {
        Client c1 = createClient("John", "Doe", LocalDate.of(1996, 4, 30));
        Client c2 = createClient("Mickel", "Gerrin", LocalDate.of(1990, 1, 2));
        List<Client> clients = Lists.newArrayList(c1, c2);
        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        FileOutputStream fileOutputStream = new FileOutputStream("./target/export-clients.xlsx");
        exportClientXLSXService.generateXLSX(fileOutputStream);
        fileOutputStream.close();

        // Attention ce test est très imparfait car il ne valide rien, il faut vérifier manuellement le fichier !
    }

    private Client createClient(String prenom, String nom, LocalDate dateNaissance) {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setDateNaissance(dateNaissance);
        return client;
    }
}