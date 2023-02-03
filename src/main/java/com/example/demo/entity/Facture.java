package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity représentant un Facture.
 */
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private boolean isPaye;

    @Column
    private LocalDate dateFinalisation;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "facture")
    private List<LigneFacture> ligneFactures = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPaye() {
        return isPaye;
    }

    public void setPaye(boolean paye) {
        isPaye = paye;
    }

    public LocalDate getDateFinalisation() {
        return dateFinalisation;
    }

    public void setDateFinalisation(LocalDate dateFinalisation) {
        this.dateFinalisation = dateFinalisation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneFacture> getLigneFactures() {
        return ligneFactures;
    }

    public void setLigneFactures(List<LigneFacture> ligneFactures) {
        this.ligneFactures = ligneFactures;
    }
}
