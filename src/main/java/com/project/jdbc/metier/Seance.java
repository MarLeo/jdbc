package com.project.jdbc.metier;

/**
 * Created by marti on 24/01/2017.
 */
public class Seance {

    private int sid;
    private Cours cours;
    private Formation formation;
    private Salle salle;

    public Seance() {}

    public Seance(Salle salle) {
        this.salle = salle;
    }

    public Seance(Cours cours, Formation formation, Salle salle) {
        this.cours = cours;
        this.formation = formation;
        this.salle = salle;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCid(Cours cours) {
        this.cours = cours;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "sid=" + sid +
                ", cours=" + cours +
                ", formation=" + formation +
                ", salle=" + salle +
                '}';
    }
}
