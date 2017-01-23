package com.project.jdbc.daoTest;

import com.project.jdbc.config.ConfigConnection;
import com.project.jdbc.dao.Formation;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by marti on 23/01/2017.
 */
public class testFormation {
    static public void main(String[] argv)
            throws ClassNotFoundException, SQLException, java.io.IOException {
        Connection _cx = null;
        DatabaseMetaData dbmd;

        // Code using the ConfigConnection class
        String username = argv[0];
        String password = argv[1];
        //String fichierProp = argv[2];


        try {
            // obtention de la connexion
            _cx = ConfigConnection.getConnection(/*fichierProp,*/username, password);

            _cx.setAutoCommit(false);

            dbmd = _cx.getMetaData(); //get MetaData to confirm connection

            System.out.println("Connection to SGBD " + dbmd.getDatabaseProductName() + " version " +
                    dbmd.getDatabaseProductVersion() + " database " +
                    dbmd.getURL() + " \n using " + dbmd.getDriverName() + " version " +
                    dbmd.getDriverVersion() + " " + "successful.\n");

            // Insertion d'une nouvelle formation
            Formation f = new Formation("Master 2 IF");
            f.save(_cx);
            _cx.commit();
            System.out.println("Formation crée et persistante : " + f.toString());

            // Récuperation des Formations  de la base
            Statement sql = null;
            sql = _cx.createStatement();

            String sqlText = "SELECT * FROM Formation";
            System.out.println("Executing this command: " + sqlText);
            ResultSet rset = sql.executeQuery(sqlText);

            /*ArrayList<Departement> plante avec la version Java 1.4 du CRIO UNIX
              Donc mettre en commentaire la ligne suivante
              et enlever les commentaires de la ligne d'après */
            ArrayList<Formation> liste = new ArrayList<Formation>();
            //ArrayList liste = new ArrayList() ;
            while (rset.next()) {
                liste.add(new Formation(rset));
            }
            rset.close();

            // Affichage
            System.out.println("\nFormation : ");
            for (int i = 0; i < liste.size(); i++) {
                System.out.println(liste.get(i).toString());
            }

            f.setNom("Master 2 ID");
            f.save(_cx);
            _cx.commit();

            // Mise à jour
            sqlText = f.getQuery() + " WHERE FID = " + f.getFid();
            System.out.println("Executing this command: " + sqlText);
            rset = sql.executeQuery(sqlText);
            if (rset.next()) {
                Formation f2 = new Formation(rset);
                System.out.println("Formation modifiée : " + f2.toString() + "\n");
                if (f2.equals(f)) System.out.println("Attention les objets f et f2 représentent le même nuplet!\n");
            }
            rset.close();

            // Suppression
            f.delete(_cx);
            _cx.commit();
            System.out.println("Executing this command: " + sqlText);
            rset = sql.executeQuery(sqlText);
            if (!rset.next())
                System.out.println("Formation non persistant!\n");
            rset.close();

        } catch (SQLException ex) {
            System.out.println("***Exception:\n" + ex);
            ex.printStackTrace();
            // ROLLBACK updates
            System.out.println("\nROLLBACK!\n" + ex);
            if (_cx != null) _cx.rollback();
        } finally {
            System.out.println("Deconnexion");
            try {
                if (_cx != null) _cx.close();
            } catch (SQLException ex) {
            }
        }
    }


}
