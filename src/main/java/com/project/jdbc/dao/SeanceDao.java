package com.project.jdbc.dao;

import com.project.jdbc.metier.Seance;

import java.sql.Connection;
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

    public Seance create(Connection connection, Seance obj) throws SQLException {
        return null;
    }

    public Seance update(Connection connection, Seance obj) throws SQLException {
        return null;
    }

    public boolean delete(Connection connection, Seance obj) throws SQLException {
        return false;
    }
}
