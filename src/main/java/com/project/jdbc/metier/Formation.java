package com.project.jdbc.metier;

/**
 * Created by marti on 23/01/2017.
 */
public class Formation {

    private int fid;
    private String nom;

    public Formation() {
    }

    public Formation(final String nom) {
        this.nom = nom;
    }

    public Formation(final int fid, final String nom) {
        this.fid = fid;
        this.nom = nom;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return "Formation " + fid + " : " + nom;
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Formation)) return false;
        final Formation obj = (Formation) other;
        return obj.getNom() == getNom();
    }

    public int hashCode() {
        return nom.hashCode();
    }


}
