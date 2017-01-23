package com.project.jdbc.dao;

/**
 * Created by marti on 23/01/2017.
 */
public class Seance {

    private int sid;
    private Cours cid;
    private Formation fid;
    private Salle salle;

    public Seance(Cours cid, Formation fid, Salle salle) {
        this.sid = -1;
        this.cid = cid;
        this.fid = fid;
        this.salle = salle;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Cours getCid() {
        return cid;
    }

    public void setCid(Cours cid) {
        this.cid = cid;
    }

    public Formation getFid() {
        return fid;
    }

    public void setFid(Formation fid) {
        this.fid = fid;
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
                ", cid=" + cid +
                ", fid=" + fid +
                ", salle=" + salle +
                '}';
    }
}
