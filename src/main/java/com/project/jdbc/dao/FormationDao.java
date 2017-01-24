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
        Statement statement = null;
        ResultSet resultSet = null;
        List<Formation> formationList = new LinkedList<Formation>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(find_all_query());
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

       /* Formation formation = new Formation();

        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                .executeQuery(find_query(fid));
        if(resultSet.first()) {
            formation.setFid(fid);
            formation = new Formation(resultSet.getString("nom"));
            return formation;
        }
        */
        return null;
    }

    public Formation create(Connection connection, Formation f) throws SQLException {
        //statement = connection.createStatement();
        //preparedStatement = connection.prepareStatement(create_query(f));
        //rs = statement.executeQuery(create_query(f));
        //int statut = preparedStatement.executeUpdate();
        //rs = preparedStatement.getGeneratedKeys();
        //if(rs.next()) {
        //   f.setFid(rs.getInt(1));
        //}

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


       /*Statement statement = null;
       statement = connection.prepareStatement(create_query(f));
       statement.executeUpdate(create_query(f));
       */
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
