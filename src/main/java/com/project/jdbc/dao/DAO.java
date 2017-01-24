package com.project.jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by marti on 23/01/2017.
 */
public abstract class DAO<T> {

    public abstract List<T> findAll(final Connection connection) throws SQLException;

    public abstract T findById(final Connection connection, final int id) throws SQLException;

    public abstract T create(final Connection connection, T obj) throws SQLException;

    public abstract T update(final Connection connection, T obj) throws SQLException;

    public abstract boolean delete(final Connection connection, T obj) throws SQLException;

}
