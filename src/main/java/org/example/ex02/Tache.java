package org.example.ex02;

public class Tache {
    private String nom;
    private String description;
    private String membre;
    private String statut;

    public Tache(String nom, String description, String membre, String statut) {
        this.nom = nom;
        this.description = description;
        this.membre = membre;
        this.statut = statut;
    }

    // Getters et setters
    public String getNomTache() {
        return nom;
    }

    public void setNomTache(String nom) {
        this.nom = nom;
    }

    public String getDescriptionTache() {
        return description;
    }

    public void setDescriptionTache(String description) {
        this.description = description;
    }

    public String getMembreTache() {
        return membre;
    }

    public void setMembreTache(String membre) {
        this.membre = membre;
    }

    public String getStatutTache() {
        return statut;
    }

    public void setStatutTache(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return nom + " (" + statut + ")";
    }
}
