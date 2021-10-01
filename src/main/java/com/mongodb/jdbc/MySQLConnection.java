package com.mongodb.jdbc;

import com.mongodb.ConnectionString;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;

public class MySQLConnection extends MongoConnection implements Connection {

    public MySQLConnection(ConnectionString cs, String database, String conversionMode) {
        super(cs, database, conversionMode);
    }

    @Override
    public Statement createStatement() throws SQLException {
        checkConnection();
        try {
            return new MySQLStatement(this, currentDB, relaxed);
        } catch (IllegalArgumentException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        checkConnection();
        try {
            return new MongoPreparedStatement(sql, this, currentDB, relaxed);
        } catch (IllegalArgumentException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return new MySQLDatabaseMetaData(this);
    }

    // --------------------------JDBC 2.0-----------------------------

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
            throws SQLException {
        if (resultSetType == ResultSet.TYPE_FORWARD_ONLY
                && resultSetConcurrency == ResultSet.CONCUR_READ_ONLY) {
            return createStatement();
        } else {
            throw new SQLFeatureNotSupportedException(
                    Thread.currentThread().getStackTrace()[1].toString());
        }
    }

    @Override
    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if (resultSetType == ResultSet.TYPE_FORWARD_ONLY
                && resultSetConcurrency == ResultSet.CONCUR_READ_ONLY) {
            return prepareStatement(sql);
        } else {
            throw new SQLFeatureNotSupportedException(
                    Thread.currentThread().getStackTrace()[1].toString());
        }
    }

    // --------------------------JDBC 3.0-----------------------------

    @Override
    public Statement createStatement(
            int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        if (resultSetType == ResultSet.TYPE_FORWARD_ONLY
                && resultSetConcurrency == ResultSet.CONCUR_READ_ONLY) {
            return createStatement();
        } else {
            throw new SQLFeatureNotSupportedException(
                    Thread.currentThread().getStackTrace()[1].toString());
        }
    }

    @Override
    public PreparedStatement prepareStatement(
            String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(
                Thread.currentThread().getStackTrace()[1].toString());
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(
                Thread.currentThread().getStackTrace()[1].toString());
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException {
        throw new SQLFeatureNotSupportedException(
                Thread.currentThread().getStackTrace()[1].toString());
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String columnNames[])
            throws SQLException {
        throw new SQLFeatureNotSupportedException(
                Thread.currentThread().getStackTrace()[1].toString());
    }
}