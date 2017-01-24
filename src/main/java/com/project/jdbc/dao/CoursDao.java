package com.project.jdbc.dao;

import com.project.jdbc.metier.Cours;
import com.project.jdbc.metier.Seance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marti on 24/01/2017.
 */
public class CoursDao extends DAO<Cours> {


    //======================================> Private Methods <========================================
    private static String find_query(final int fid) {
        return null;
    }

    private static String create_query(final Cours c) {
        return null;
    }

    private static String update_query(Formation formation) {
        return null;
    }

    private static String delete_query(Formation formation) {
        return null;
    }

    private static String find_all_query() {
        return "SELECT * from Cours";
    }

    public List<Cours> findAll(Connection connection) throws SQLException {

        return null;
    }

    public Cours findById(Connection connection, int cid) throws SQLException {
        Cours cours = new Cours();

        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                .executeQuery("SELECT * FROM Cours WHERE cid = " + cid);
        if (resultSet.first()) {
            SeanceDao seanceDao = new SeanceDao();
            List<Seance> seances = new LinkedList<Seance>();
            resultSet.beforeFirst();
            while (resultSet.next() && resultSet.getInt("cid") != 0) {
                seances.add(seanceDao.findById(connection, resultSet.getInt("cid")));
            }
            resultSet.first();
            cours = new Cours(cid, new FormationDao().findById(connection, resultSet.getInt("fid")), resultSet.getString("nom"), seances);
        }
        return cours;
    }

    public Cours create(Connection connection, Cours cours) throws SQLException {
        // Check if formation exists, and create it
        if (cours.getFormation().getFid() == 0) {
            FormationDao formationDao = new FormationDao();
            cours.setFormation(formationDao.create(connection, cours.getFormation()));
        }

        //ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT nextval('cours_cid_seq') as id" );

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Cours() VALUES(?, ?, ? )");
        preparedStatement.setInt(1, cours.getCid());
        preparedStatement.setInt(2, cours.getFormation().getFid());
        preparedStatement.setString(3, cours.getNom());
        preparedStatement.executeUpdate();

        // Création des liens vers les séances
        // si la séance n'existe pas en base , on la crée
        for (Seance seance : cours.getSeances()) {
            if (seance.getSid() == 0) {
                SeanceDao seanceDao = new SeanceDao();
                seance = seanceDao.create(connection, seance);
            }
            // On récupére la prochaine valeur de la séquence
            ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT NEXTVAL(seance_sid_seq) as sid");
            if (resultSet.first()) {
                int sid = resultSet.getInt("sid");
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO Seance() VALUES(?, ?, ?, ?, ?)");
                preparedStatement1.setInt(1, sid);
                preparedStatement1.setInt(2, cours.getCid());
                preparedStatement1.setInt(3, cours.getFormation().getFid());
                preparedStatement1.setInt(4, seance.getSalle().getNumSalle());
                preparedStatement1.setString(5, seance.getSalle().getBatiment());
                preparedStatement1.executeUpdate();
            }
        }

        return cours;
    }

    public Cours update(Connection connection, Cours cours) throws SQLException {

        // on met à jour la liste des séances
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Cours SET nom = '" + cours.getNom() + "'" +
                "WHERE cid = " + cours.getCid());
        FormationDao formationDao = new FormationDao();
        formationDao.update(connection, cours.getFormation());
        preparedStatement.executeUpdate();

        // Création des liens vers les séances
        // Si la séance n'existe pas en base, on la crée
        for (Seance seance : cours.getSeances()) {
            SeanceDao seanceDao = new SeanceDao();

            // si la séance n'existe pas, on la créé avec sa jointure
            if (seance.getSid() == 0) {
                seance = seanceDao.create(connection, seance);

                // on récupére la prochaine valeur de la séance
                ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                        .executeQuery("SELECT NEXTVAL(seance_sid_seq) as sid");
                if (resultSet.first()) {
                    int sid = resultSet.getInt("sid");
                    PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO Seance() VALUES(?, ?, ?, ?, ?)");
                    preparedStatement1.setInt(1, sid);
                    preparedStatement1.setInt(2, cours.getCid());
                    preparedStatement1.setInt(3, cours.getFormation().getFid());
                    preparedStatement1.setInt(4, seance.getSalle().getNumSalle());
                    preparedStatement1.setString(5, seance.getSalle().getBatiment());
                    preparedStatement1.executeUpdate();
                }
            } else {
                seanceDao.update(connection, seance);
            }
        }

        return cours;
    }

    public boolean delete(Connection connection, Cours cours) throws SQLException {
        // delete from Seance table
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeUpdate("DELETE FROM Seance WHERE cid = " + cours.getCid());

        // delete from Cours table
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeUpdate("DELETE Cours Seance WHERE cid = " + cours.getCid());

        return findById(connection, cours.getCid()) == null;
    }


}
