package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
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

@RunWith(MockitoJUnitRunner.class)
public class ExportFactureXLSXServiceTest {

    @Mock
    private FactureRepository factureRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ExportFactureXLSXService exportFactureXLSXService;

    @Test
    public void testGenerateXLSX() throws IOException {
        // On a copier/coller le code de la classe InitData, il faudrait refactoriser !
        Article a1 = createArticle("Les conserves de viande de licorne", 22.98, "https://static.hitek.fr/img/actualite/2016/08/26/41gn6tpvqtl.jpg", 1);
        Article a2 = createArticle("Wenger Couteau suisse géant", 46.39, "https://static.hitek.fr/img/actualite/2016/08/26/61abqa-gt8s-sx522.jpg", 40);
        Article a3 = createArticle("PAPIER TOILETTE DONALD TRUMP", 4.99, "https://static.hitek.fr/img/actualite/2016/08/26/61cb4xnrbol-sx522.jpg", 100);
        Article a6 = createArticle("UN AFFINEUR DE VISAGE", 52, "https://static.hitek.fr/img/actualite/2016/08/26/w_41r-1yapf5l.jpg", 20);

        Client c1 = createClient("John", "Doe", LocalDate.of(1996, 4, 30));
        Client c2 = createClient("Mickael", "Gerrin", LocalDate.of(1990, 1, 2));
        Client c3 = createClient("Jean-Michel", "Napasdargent", LocalDate.of(1980, 1, 2));

        Facture f1 = createFacture(1l,true, LocalDate.of(2023, 2, 1), c1);
        LigneFacture lf11 = createLigneFacture(1, f1, a1);
        LigneFacture lf12 = createLigneFacture(6, f1, a3);

        Facture f2 = createFacture(2l,true, LocalDate.of(2023, 2, 15), c1);
        LigneFacture lf21 = createLigneFacture(1, f2, a1);
        LigneFacture lf22 = createLigneFacture(6, f2, a3);
        LigneFacture lf23 = createLigneFacture(4, f2, a6);

        Facture f3 = createFacture(3l,true, LocalDate.of(2022, 2, 15), c2);
        LigneFacture lf31 = createLigneFacture(1, f3, a2);

        c1.addFacture(f1);
        c1.addFacture(f2);
        c2.addFacture(f3);

        Mockito.when(clientRepository.findAll()).thenReturn(Lists.newArrayList(c1, c2, c3));

        FileOutputStream os = new FileOutputStream("./target/export-factures.xlsx");
        exportFactureXLSXService.generateXLSX(os);
        os.close();
        // toujours pas d'assertion ici
    }

    private LigneFacture createLigneFacture(int quantite, Facture facture, Article article) {
        LigneFacture ligneFacture = new LigneFacture();
        ligneFacture.setQuantite(quantite);
        ligneFacture.setFacture(facture);
        ligneFacture.setArticle(article);
        facture.getLigneFactures().add(ligneFacture);
        return ligneFacture;
    }

    private Facture createFacture(Long id, boolean isPaye, LocalDate dateFinalisation, Client client) {
        Facture facture = new Facture();
        facture.setId(id);
        facture.setPaye(isPaye);
        facture.setDateFinalisation(dateFinalisation);
        facture.setClient(client);
        return facture;
    }

    private Client createClient(String prenom, String nom, LocalDate dateNaissance) {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setDateNaissance(dateNaissance);
        return client;
    }

    private Article createArticle(String libelle, double prix, String imageUrl, Integer stock) {
        Article a1 = new Article();
        a1.setLibelle(libelle);
        a1.setPrix(prix);
        a1.setImageUrl(imageUrl);
        a1.setStock(stock);
        return a1;
    }
}