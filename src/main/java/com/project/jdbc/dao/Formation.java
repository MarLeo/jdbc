package com.project.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by marti on 23/01/2017.
 */
public class Formation {

    private static String _query = "SELECT * FROM Formation";
    private int fid;
    private String nom;
    private boolean _builtFromDB;

    public Formation() {
        _builtFromDB = false;
    }

    public Formation(String nom) {
        this.fid = -1;
        this.nom = nom;
    }

    public Formation(ResultSet rs) throws SQLException {
        fid = rs.getInt("FID");
        nom = rs.getString("Nom");
        _builtFromDB = true;
    }

    private String _update() {
        return "UPDATE Formation SET Nom='" + nom +
                "' WHERE FID=" + fid;
    }

    private String _insert() {
        return "INSERT INTO Formation"
                + " VALUES(nextval('formation_FID_seq'), '" +
                nom + "')";
    }

    private String _delete() {
        return "DELETE FROM Formation"
                + " WHERE FID = " + fid;
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

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Formation)) return false;
        final Formation obj = (Formation) other;
        return obj.getNom() == getNom();
    }

    public int hashCode() {
        return nom.hashCode();
    }

    public void save(Connection cx) throws SQLException {
        Statement s = cx.createStatement();
        if (_builtFromDB) {
            System.out.println("Executing this command: " + _update() + "\n");
            s.executeUpdate(_update());
        } else {
            // Récuperation de la clé générée par la séquence

            // Code ne fonctionnant pas avec le driver JDBC de PostgreSQL
            // Mais apparemment avec celui de MySQL OUI.
            /*s.executeUpdate(_insert(), Statement.RETURN_GENERATED_KEYS);
            ResultSet r = s.getGeneratedKeys();
            while(r.next())
                id = r.getInt(1);
                */


            System.out.println("Executing this command: " + _insert() + "\n");
            s.executeUpdate(_insert());
            _builtFromDB = true;

            // Pour récuperer la clé générée sous PostgreSQL
            ResultSet rset = s.executeQuery("SELECT last_value FROM formation_fid_seq");
            if (rset.next()) {
                //Column numbered from 1 (not from zero)
                fid = rset.getInt(1);
            }
        }
    }

    public void delete(Connection cx) throws SQLException {
        Statement s = cx.createStatement();
        if (_builtFromDB) {
            System.out.println("Executing this command: " + _delete() + "\n");
            s.executeUpdate(_delete());
        } else System.out.println("Objet non persistant!");
    }

    public String getQuery() {
        return _query;
    }

    public String toString() {
        return "Formation " + fid + " : " + nom;
    }
}
