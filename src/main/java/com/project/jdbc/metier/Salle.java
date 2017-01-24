package com.project.jdbc.metier;

/**
 * Created by marti on 24/01/2017.
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

}
