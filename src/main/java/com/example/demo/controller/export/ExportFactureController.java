package com.example.demo.controller.export;

import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.export.ExportFactureXLSXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("export/factures")
public class ExportFactureController {

    @Autowired
    private ExportFactureXLSXService exportFactureXLSXService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FactureRepository factureRepository;

    @GetMapping("xlsx")
    public void exportXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportFactureXLSXService.generateXLSX(outputStream);
    }
}
