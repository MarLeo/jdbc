package com.project.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by marti on 23/01/2017.
 */
public class Departement {

    private static String _query = "SELECT * FROM Departement";
    private int id;
    private String nom;
    private boolean _builtFromDB;

    public Departement() {
        _builtFromDB = false;
    }

    public Departement(String nom) {
        this.nom = nom;
        id = -1;
        _builtFromDB = false;
    }

    public Departement(ResultSet rs) throws SQLException {
        id = rs.getInt("Departement_ID");
        nom = rs.getString("Nom_Departement");
        _builtFromDB = true;
    }

    private String _update() {
        return "UPDATE Departement SET Nom_Departement='" + nom +
                "' WHERE Departement_ID=" + id;
    }

    private String _insert() {
        return "INSERT INTO Departement"
                + " VALUES(nextval('departement_departement_id_seq'), '" +
                nom + "')";
    }

    private String _delete() {
        return "DELETE FROM Departement"
                + " WHERE Departement_ID = " + id;
    }

    public String toString() {
        return "Département " + id + " : " + nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Departement)) return false;
        final Departement obj = (Departement) other;
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
            ResultSet rset = s.executeQuery("SELECT last_value FROM departement_departement_id_seq");
            if (rset.next()) {
                //Column numbered from 1 (not from zero)
                id = rset.getInt(1);
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
}
