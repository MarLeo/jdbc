package com.project.jdbc;

import com.project.jdbc.config.ConfigConnection;
import com.project.jdbc.dao.DAO;
import com.project.jdbc.dao.FormationDao;
import com.project.jdbc.metier.Formation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by marti on 23/01/2017.
 */
public class MainTest {

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
                    dbmd.getURL() + " \nusing " + dbmd.getDriverName() + " version " +
                    dbmd.getDriverVersion() + " " + "successful.\n");


            DAO<Formation> formationDAO = new FormationDao(); // instantiation du dao formation
            // Insertion d'une nouvelle formation
            Formation created_formation = formationDAO.create(_cx, new Formation("Master 2 ID"));
            System.out.println("Formation " + created_formation.getNom() + " with id " + created_formation.getFid() + " created ");

            // find a new formation
            Formation formation_finded = formationDAO.findById(_cx, created_formation.getFid());
            System.out.println("The formation with id " + created_formation.getFid() + " is " + formation_finded.getNom());

             //updating a formation
            formation_finded.setNom("Master 2 SITN");
            Formation formation_update = formationDAO.update(_cx, formation_finded);
            System.out.println("The name of formation " + formation_finded.getNom() + " has been renamed to " + formation_update.getNom());

            // deleting a formation
            //formationDAO.delete(_cx, formation_update);


            // finding all data
            List<Formation> formationList = formationDAO.findAll(_cx);
            System.out.println("Formations in the DB are : " + formationList.toString());


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
