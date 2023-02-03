package com.example.demo.controller.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.export.ExportClientXLSXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 * Controller pour réaliser l'export des clients.
 */
@Controller
@RequestMapping("export/clients")
public class ExportClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ExportClientXLSXService exportClientXLSXService;

    /**
     * Export des clients au format CSV.
     */
    @GetMapping("csv")
    public void exportCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();
        writer.println("Nom;Prénom;Age");
        for (Client client : clientRepository.findAll()) {
            writer.print(client.getNom());
            writer.print(";");
            writer.print(client.getPrenom());
            writer.print(";");
            writer.print(client.getAge(LocalDate.now()));
            writer.println();
        }
    }

    @GetMapping("xlsx")
    public void exportXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"export-client.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportClientXLSXService.generateXLSX(outputStream);
    }

}
