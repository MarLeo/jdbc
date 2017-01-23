package com.project.jdbc.dao;

import java.util.List;

/**
 * Created by marti on 23/01/2017.
 */
public class Cours {

    private int cid;
    private Formation fid;
    private String nom;
    private List<Seance> seances;

    public Cours(int cid, Formation fid, String nom) {
        this.cid = cid;
        this.fid = fid;
        this.nom = nom;
    }

    private void addSeance(Seance s) {
        seances.add(s);
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Formation getFid() {
        return fid;
    }

    public void setFid(Formation fid) {
        this.fid = fid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "cid=" + cid +
                ", fid=" + fid +
                ", nom='" + nom + '\'' +
                ", seances=" + seances +
                '}';
    }
}
