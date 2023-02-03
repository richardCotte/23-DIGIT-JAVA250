package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;

@Service
@Transactional
public class ExporArticleCSVService {

    @Autowired
    private ArticleRepository articleRepository;

    public void generateCSV(PrintWriter writer) {
        writer.println("Libellé;Prix");
        for (Article article : articleRepository.findAll()) {
            writer.print(article.getLibelle());
            writer.print(";");
            writer.print(article.getPrix());
            writer.println();
        }
    }
}
