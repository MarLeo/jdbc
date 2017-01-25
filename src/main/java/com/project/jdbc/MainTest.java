package com.project.jdbc;

import com.project.jdbc.config.ConfigConnection;
import com.project.jdbc.dao.CoursDao;
import com.project.jdbc.dao.DAO;
import com.project.jdbc.dao.FormationDao;
import com.project.jdbc.dao.SeanceDao;
import com.project.jdbc.metier.Cours;
import com.project.jdbc.metier.Formation;
import com.project.jdbc.metier.Salle;
import com.project.jdbc.metier.Seance;

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
        Connection connection = null;
        DatabaseMetaData dbmd;

        // Code using the ConfigConnection class
        String username = argv[0];
        String password = argv[1];
        //String fichierProp = argv[2];


        try {
            // obtention de la connexion
            connection = ConfigConnection.getConnection(/*fichierProp,*/username, password);

            connection.setAutoCommit(false);

            dbmd = connection.getMetaData(); //get MetaData to confirm connection

            System.out.println("Connection to SGBD " + dbmd.getDatabaseProductName() + " version " +
                    dbmd.getDatabaseProductVersion() + " database " +
                    dbmd.getURL() + " \nusing " + dbmd.getDriverName() + " version " +
                    dbmd.getDriverVersion() + " " + "successful.\n");


            DAO<Formation> formationDAO = new FormationDao(); // instantiation du dao formation

            // Insertion d'une nouvelle formation
            Formation created_formation = formationDAO.create(connection, new Formation("Master 2 ID"));
            System.out.println("Formation " + created_formation.getNom() + " with id " + created_formation.getFid() + " created ");

            // find a new formation
            Formation formation_finded = formationDAO.findById(connection, created_formation.getFid());
            System.out.println("The formation with id " + created_formation.getFid() + " is " + formation_finded.getNom());

             //updating a formation
            formation_finded.setNom("Master 2 SITN");
            Formation formation_update = formationDAO.update(connection, formation_finded);
            System.out.println("The name of formation " + formation_finded.getNom() + " has been renamed to " + formation_update.getNom());

            // deleting a formation
            //formationDAO.delete(connection, formation_update);


            // finding all data
            List<Formation> formationList = formationDAO.findAll(connection);
            System.out.println("Formations in the DB are : " + formationList.toString());


            // Create  3 rooms
            Salle s1 = new Salle(1, "A"); // =====> salle A
            Salle s2 = new Salle(2,"B"); // =====> salle B
            Salle s3 = new Salle(3,"C"); // ======> salle C

            CoursDao coursDao = new CoursDao(); // Instantiation de CoursDao
            SeanceDao seanceDao = new SeanceDao(); // Instantiation de SeanceDao

            // Create  2 Cours
            Cours c1 = new Cours("Finance de marchés", created_formation);
            //Cours c2 = new Cours();


            // Insert a new Cours into DB
            Cours created_cours = coursDao.create(connection, c1);
            System.out.println("A new course  with name :" + created_cours.getNom() + " have been created with id " + created_cours.getCid() + " and formation " + created_cours.getFormation());

            // Find a collection of cours
            Cours find_cours = coursDao.findById(connection, created_cours.getCid(), created_cours.getFormation().getFid());
            System.out.println("The list of courses finded is : " + find_cours.toString());

            // update a course
            created_cours.setNom("Optimization Finance");
            Cours updated_cours = coursDao.update(connection, created_cours);
            System.out.println("The update course is : " + updated_cours.toString());

            // Instantation d'une séance
            Seance seance = new Seance(updated_cours, updated_cours.getFormation(), s1);

            // Insert Seance into DB
            Seance create_seance = seanceDao.create(connection, seance);
            System.out.println("A new seance with id " + create_seance.getSid() + " has been created : " + create_seance.toString());

            // find seance
             Seance find_seance = seanceDao.findById(connection, /*create_seance.getSid()*/1, /*create_seance.getCours().getCid()*/2, /*create_seance.getFormation().getFid()*/2);
             System.out.println("Seance with id " + find_seance.getSid() + " for formation " + find_seance.getFormation().getFid() + " and cours " + find_seance.getCours().getCid() + " finded in room " + find_seance.getSalle());


        } catch (SQLException ex) {
            System.out.println("***Exception:\n" + ex);
            ex.printStackTrace();
            // ROLLBACK updates
            System.out.println("\nROLLBACK!\n" + ex);
            if (connection != null) connection.rollback();
        } finally {
            System.out.println("Deconnexion");
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
            }
        }


    }


}
