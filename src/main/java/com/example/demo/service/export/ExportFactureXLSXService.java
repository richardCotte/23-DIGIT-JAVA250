package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class ExportFactureXLSXService {

    @Autowired
    private ClientRepository clientRepository;

    public void generateXLSX(OutputStream outputStream) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        CellStyle alignRightBoldStyle = workbook.createCellStyle();
        alignRightBoldStyle.setFont(boldFont);
        alignRightBoldStyle.setAlignment(HorizontalAlignment.RIGHT);

        for (Client client : clientRepository.findAll()) {

            List<Facture> factures = client.getFactures();

            if (factures.size() > 0) {

                Sheet clientSheet = workbook.createSheet(client.getPrenom() + " " + client.getNom());

                int iNomCell = 0;
                int iPrenomCell = 1;
                int iBirthdateCell = 2;
                int iFactureCell = 3;

                displayClientInfo(clientSheet, "Nom :", client.getNom(), iNomCell);
                displayClientInfo(clientSheet, "Prénom :", client.getPrenom(), iPrenomCell);
                displayClientInfo(clientSheet, "Année de naissance :", client.getDateNaissance().getYear(), iBirthdateCell);

                Row row = clientSheet.createRow(iFactureCell);
                Cell factureHeaderCell = row.createCell(0);
                factureHeaderCell.setCellStyle(boldStyle);
                factureHeaderCell.setCellValue(factures.size() + " facture(s) :");
                int iClientSheetFactureRow = 1;
                for (Facture facture : factures) {
                    Cell factureValueCell = row.createCell(iClientSheetFactureRow);
                    factureValueCell.setCellValue(facture.getId());
                    iClientSheetFactureRow++;
                }

                int iHeaderRow = 0;

                int iDesignCol = 0;
                int iQuantityCol = 1;
                int iPriceCol = 2;

                for (Facture facture : factures) {
                    Sheet factureSheet = workbook.createSheet("Facture N°" + facture.getId());

                    Row headerRow = factureSheet.createRow(iHeaderRow);

                    Cell designHeaderCell = headerRow.createCell(iDesignCol);
                    designHeaderCell.setCellStyle(boldStyle);
                    designHeaderCell.setCellValue("Désignation");

                    Cell quantityHeaderCell = headerRow.createCell(iQuantityCol);
                    quantityHeaderCell.setCellStyle(boldStyle);
                    quantityHeaderCell.setCellValue("Quantité");

                    Cell priceHeaderCell = headerRow.createCell(iPriceCol);
                    priceHeaderCell.setCellStyle(boldStyle);
                    priceHeaderCell.setCellValue("Prix Unitaire");

                    int iFactureSheetRow = iHeaderRow + 1;
                    for (LigneFacture ligneFacture : facture.getLigneFactures()) {
                        displayLigneFacture(ligneFacture, factureSheet, iDesignCol, iQuantityCol, iPriceCol, iFactureSheetRow);
                        iFactureSheetRow++;
                    }

                    Row totalRow = factureSheet.createRow(iFactureSheetRow);

                    Cell totalLabelCell = totalRow.createCell(iDesignCol);
                    totalLabelCell.setCellStyle(alignRightBoldStyle);
                    totalLabelCell.setCellValue("Total");

                    factureSheet.addMergedRegion(new CellRangeAddress(
                            iFactureSheetRow,
                            iFactureSheetRow,
                            iDesignCol,
                            iQuantityCol
                    ));

                    String formulaStr = "SOMMEPROD(B" + (iHeaderRow + 2) + ":B" + iFactureSheetRow +
                            ",C" + (iHeaderRow + 2) + ":C" + iFactureSheetRow + ")";

                    Cell totalValueCell = totalRow.createCell(iPriceCol);
                    totalValueCell.setCellFormula(formulaStr);
                    //Peut ne pas marcher selon les versions d'excel (si ça marche pas sur la votre essayer d'enlever
                    // le "@" dans la formule du fichier excel) !

                    factureSheet.autoSizeColumn(iDesignCol);
                    factureSheet.autoSizeColumn(iQuantityCol);
                    factureSheet.autoSizeColumn(iPriceCol);

                }

                clientSheet.autoSizeColumn(0);
                clientSheet.autoSizeColumn(1);

            }
        }
        workbook.write(outputStream);
        workbook.close();
    }

    public void displayClientInfo(Sheet sheet, String header, Object value, Integer rowNbr) {
        Row row = sheet.createRow(rowNbr);
        Cell headerCell = row.createCell(0);
        headerCell.setCellValue(header);
        Cell valueCell = row.createCell(1);
        if (value instanceof String) {
            valueCell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            valueCell.setCellValue((Integer) value);
        }
    }

    public void displayLigneFacture(LigneFacture ligneFacture, Sheet sheet, int designCol, int quantCol, int priceCol, int rowNbr) {
        Row ligneFactureRow = sheet.createRow(rowNbr);

        Cell designCell = ligneFactureRow.createCell(designCol);
        designCell.setCellValue(ligneFacture.getArticle().getLibelle());

        Cell quantCell = ligneFactureRow.createCell(quantCol);
        quantCell.setCellValue(ligneFacture.getQuantite());

        Cell priceCell = ligneFactureRow.createCell(priceCol);
        priceCell.setCellValue(ligneFacture.getArticle().getPrix());
    }

}
