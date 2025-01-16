package org.example.ex02;

public class Membre {
    private final String nom;
    public Membre(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    @Override
    public String toString() {
        return nom;
    }
}
