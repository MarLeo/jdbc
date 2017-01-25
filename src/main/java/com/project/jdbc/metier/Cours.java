package com.project.jdbc.metier;

import java.util.List;

/**
 * Created by marti on 24/01/2017.
 */
public class Cours {

    //private static AtomicInteger counter = new AtomicInteger(2);
    private int cid;
    private Formation f;
    private String nom;
    private List<Seance> seances;

    public Cours() {
    }

    public Cours(final int cid) {
        this.cid = cid;
    }

    public Cours(final String nom, final Formation formation) {
        this.nom = nom;
        this.f = formation;
    }

    public Cours(int cid, Formation f, String nom, final List<Seance> seances) {
        this.cid = cid;
        this.f = f;
        this.nom = nom;
        this.seances = seances;
    }

    public void addSeance(Seance s) {
        seances.add(s);
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Formation getFormation() {
        return f;
    }

    public void setFormation(Formation fid) {
        this.f = f;
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

    public Seance getSeance(int sid) {
        return seances.get(sid);
    }

    @Override
    public String toString() {
        return " Cours  Cours_id : " + this.cid + " formation_id : " + f.getFid() + " nom : " + nom + " seances : " + seances;
    }

}
