package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

@Service
@Transactional
public class ExportClientXLSXService {

    @Autowired
    private ClientRepository clientRepository;

    public void generateXLSX(OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(); //=> Fichier excel
        Sheet sheet = workbook.createSheet("Clients");

        int iRow = 0;
        Row headerRow = sheet.createRow(iRow++);

        int iColPrenom = 0;
        int iColNom = 1;
        int iColAge = 2;

        Cell headerPrenom = headerRow.createCell(iColPrenom);
        headerPrenom.setCellValue("Prénom");
        Cell headerNom = headerRow.createCell(iColNom);
        headerNom.setCellValue("Nom");
        Cell headerAge = headerRow.createCell(iColAge);
        headerAge.setCellValue("Age");

        for (Client client : clientRepository.findAll()) {
            Row row = sheet.createRow(iRow++);
            Cell cellPrenom = row.createCell(iColPrenom);
            cellPrenom.setCellValue(client.getPrenom());
            Cell cellNom = row.createCell(iColNom);
            cellNom.setCellValue(client.getNom());
            Cell cellAge = row.createCell(iColAge);
            cellAge.setCellValue(client.getAge(LocalDate.now()));
        }

        workbook.write(outputStream);
        workbook.close();
    }

}
