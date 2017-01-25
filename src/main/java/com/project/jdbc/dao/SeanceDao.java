package com.project.jdbc.dao;

import com.project.jdbc.metier.Salle;
import com.project.jdbc.metier.Seance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by marti on 24/01/2017.
 */
public class SeanceDao extends DAO<Seance> {

    public List<Seance> findAll(Connection connection) throws SQLException {
        return null;
    }

    public Seance findById(Connection connection, int id) throws SQLException {
        return null;
    }

    public Seance findById(Connection connection, int sid, int cid, int fid) throws SQLException {
        Seance seance = new Seance();
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                                         .executeQuery(find_query(sid, cid, fid));
        if (resultSet.first()) {
            CoursDao coursDao = new CoursDao();
            FormationDao formationDao = new FormationDao();
            Salle salle = new Salle(resultSet.getInt("numsalle"), resultSet.getString("nombatiment"));
            seance = new Seance(coursDao.findById(connection, cid), formationDao.findById(connection, fid), salle);
            seance.setSid(sid);
            connection.commit();
            return seance;
        }
        return null;
    }

    public Seance create(Connection connection, Seance seance) throws SQLException {
        // récupére le prochain id (SERIAL)
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeQuery("SELECT nextval('seance_sid_seq') as sid");
        if(resultSet.first()) {
            int sid = resultSet.getInt("sid");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO seance() VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, sid);
            preparedStatement.setInt(2, seance.getCours().getCid());
            preparedStatement.setInt(3, seance.getFormation().getFid());
            preparedStatement.setInt(5, seance.getSalle().getNumSalle());
            preparedStatement.setString(5, seance.getSalle().getBatiment());
            seance.setSid(sid);
            connection.commit();
        }
        return seance;
    }

    public Seance update(Connection connection, Seance obj) throws SQLException {
        return null;
    }

    public boolean delete(Connection connection, Seance obj) throws SQLException {
        return false;
    }



    // =============================> Private Methods <========================================

    private static String find_query(final int sid, final int cid, final int fid) {
        return "SELECT * FROM seance WHERE sid = " + sid  + " AND cid = " + cid + " AND fid = " + fid;
    }







}
