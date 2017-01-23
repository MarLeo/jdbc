package com.project.jdbc.dao;

/**
 * Created by marti on 23/01/2017.
 */
public class Salle {

    private int numSalle;
    private String batiment;

    public Salle(String batiment) {
        this.numSalle = -1;
        this.batiment = batiment;
    }

    public int getNumSalle() {
        return numSalle;
    }

    public void setNumSalle(int numSalle) {
        this.numSalle = numSalle;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "numSalle=" + numSalle +
                ", batiment='" + batiment + '\'' +
                '}';
    }
}
