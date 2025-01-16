package org.example.ex02;

public class Tache {
    private final String nom;
    private final String statut;

    public Tache(String nom, String statut) {
        this.nom = nom;
        this.statut = statut;
    }

    @Override
    public String toString() {
        return nom + " (" + statut + ")";
    }
}
