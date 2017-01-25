package com.project.jdbc.dao;

import com.project.jdbc.metier.Formation;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marti on 23/01/2017.
 */
public class FormationDao extends DAO<Formation> {

    @Override
    public List<Formation> findAll(final Connection connection) throws SQLException {
        List<Formation> formationList = new LinkedList<Formation>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(find_all_query());
        while (resultSet.next()) {
            Formation f = new Formation();
            f.setFid(resultSet.getInt("fid"));
            f.setNom(resultSet.getString("nom"));
            formationList.add(f);
        }
        return formationList;
    }

    public Formation findById(Connection connection, int fid) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * from Formation");
        while (resultSet.next()) {
            Formation formation = new Formation();
            formation.setFid(resultSet.getInt("fid"));
            formation.setNom(resultSet.getString("nom"));
            connection.commit();
            return formation;
        }
        return null;
    }

    public Formation create(Connection connection, Formation f) throws SQLException {
        // récupére le prochain id (SERIAL)
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeQuery("SELECT nextval('formation_fid_seq') as fid");
        if (resultSet.first()) {
            int fid = resultSet.getInt("fid");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Formation (fid, nom) VALUES(?, ?)");
            preparedStatement.setInt(1, fid);
            preparedStatement.setString(2, f.getNom());
            preparedStatement.executeUpdate();
            connection.commit();
            f = this.findById(connection, fid);
        }

        return f;
    }

    public Formation update(Connection connection, Formation f) throws SQLException {
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeUpdate(update_query(f));
        f = this.findById(connection, f.getFid());
        connection.commit();
        return f;
    }

    public boolean delete(Connection connection, Formation f) throws SQLException {
        connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeUpdate(delete_query(f));
        connection.commit();
        return this.findById(connection, f.getFid()) != null;
    }



    //======================================> Private Methods <========================================
    private static String find_query(final int fid) {
        return "SELECT * from Formation WHERE fid = " + fid;
    }

    private static String create_query(final Formation f) {
        return "INSERT INTO formation"
                + " VALUES(nextval('formation_fid_seq'), '" +
                f.getNom() + "')";
    }

    private static String update_query(Formation formation) {
        return "UPDATE formation SET nom = '" + formation.getNom() + "'" +
                " WHERE fid = " + formation.getFid();
    }

    private static String delete_query(Formation formation) {
        return "DELETE FROM formation WHERE fid = " + formation.getFid();
    }

    private static String find_all_query() {
        return "SELECT * from Formation ORDER BY fid";
    }


}
