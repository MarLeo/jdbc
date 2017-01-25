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


    public List<Cours> findAll(Connection connection) throws SQLException {

        return null;
    }

    public Cours findById(Connection connection, final int cid, final int fid) throws SQLException {
        Cours cours = new Cours();
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                .executeQuery(find_query(cid, fid));
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
        connection.commit();
        return cours;
    }

    public Cours findById(Connection connection, int cid) throws SQLException {
        Cours cours = new Cours();
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                .executeQuery(find_query(cid));
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
        connection.commit();
        return cours;
    }

    public Cours create(Connection connection, Cours cours) throws SQLException {
        // récupére le prochain le cid max
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeQuery(find_max_key());
        if (resultSet.first()) {
            int max_id  = resultSet.getInt("cid") + 1;
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query());
            preparedStatement.setInt(1, max_id);
            preparedStatement.setInt(2, cours.getFormation().getFid());
            preparedStatement.setString(3, cours.getNom());
            cours.setCid(max_id);
            preparedStatement.executeUpdate();
        }
        connection.commit();
        return cours;
    }

    public Cours update(Connection connection, Cours cours) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(update_query(cours));
        FormationDao formationDao = new FormationDao();
        formationDao.update(connection, cours.getFormation());
        int size = 0;
       /* for(Seance seance : cours.getSeances()) {
            size++;
            SeanceDao  seanceDao = new SeanceDao();
            if(seance.getSid() != 0)
                seanceDao.update(connection, cours.getSeance(size));
        }*/
        preparedStatement.executeUpdate();
        connection.commit();
        return cours;
    }

    public boolean delete(Connection connection, Cours cours) throws SQLException {
        // delete from Seance table
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeUpdate("DELETE FROM Seance WHERE cid = " + cours.getCid());

        // delete from Cours table
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeUpdate("DELETE FROM Cours WHERE cid = " + cours.getCid());
        connection.commit();
        return findById(connection, cours.getCid()) == null;
    }


    //======================================> Private Methods <========================================

    private static String find_max_key() {
        return "SELECT cid FROM cours ORDER BY cid DESC LIMIT 1";
    }

    private static String find_query(final int cid) {
        return "SELECT * FROM Cours WHERE cid = " + cid;
    }

    private static String find_query(final int cid , final int fid) {
        return "SELECT * FROM Cours WHERE cid = " + cid + " AND fid = " + fid;
    }

    private static String insert_query() {
        return "INSERT INTO Cours (cid, fid, nom) VALUES(?, ?, ?)";
    }

    private static String update_query(final Cours cours) {
        return "UPDATE Cours SET nom = '" + cours.getNom() + "'" +
                "WHERE cid = " + cours.getCid() + " AND fid = " + cours.getFormation().getFid();
    }

    private static String delete_query(final Cours cours) {
        return "DELETE FROM Seance WHERE cid = " + cours.getCid();
    }

    private static String find_all_query() {
        return "SELECT * from Cours ORDER BY (cid, fid)";
    }




}
