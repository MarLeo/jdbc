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
            Formation f = new Formation("Medecine");
            // System.out.println("Executing this command: " + sqlText + "\n");
            formationDAO.create(_cx, f);
            System.out.println("Formation " + f.toString() + " created");

            // find a new formation
            Formation formation = formationDAO.findById(_cx, 7);
            System.out.println("The corresponding formation is : " + formation.toString());

            // updating a formation
            formation.setNom("Master 2 SITN");
            Formation formation_update = formationDAO.update(_cx, formation);
            System.out.println("The updating formation is : " + formation_update.toString());

            // deleting a formation
            formationDAO.delete(_cx, formation_update);


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
