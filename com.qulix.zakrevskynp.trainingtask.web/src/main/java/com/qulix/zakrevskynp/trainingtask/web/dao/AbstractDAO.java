package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.List;

public class AbstractDAO<T> {

    protected List<T> getList(DaoUtil daoUtil, String selectQuery, String error) {
        return (List<T>) ExecuteDAO.execute(error, new Executable() {
            @Override
            public Object exec(Connection connection) throws SQLException {
                ResultSet resultSet = null;
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                    resultSet = preparedStatement.executeQuery();
                    return daoUtil.resultSetToList(resultSet);
                }
                finally {
                    closeResultSet(resultSet);
                }
            }
        });
    }

    protected void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    protected void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    protected void remove(int id, String removeQuery, String error)  {
        ExecuteDAO.execute(error, new Executable() {
            @Override
            public Object exec(Connection connection) throws SQLException {
                try (PreparedStatement preparedStatement = connection.prepareStatement(removeQuery)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.execute();
                    connection.commit();
                }
                return true;
            }
        });
    }

    protected void add(DaoUtil daoUtil, T entity, String insertQuery, String error)  {
            ExecuteDAO.execute(error, new Executable() {
                @Override
                public Object exec(Connection connection) throws SQLException {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        daoUtil.setPreparedStatement(preparedStatement, entity);
                        preparedStatement.execute();
                        connection.commit();
                    }
                    return true;
                }
            });
    }

    protected T getById(DaoUtil daoUtil, int id, String getByIdQuery, String error)  {
            return (T) ExecuteDAO.execute(error, new Executable() {
                @Override
                public Object exec(Connection connection) throws SQLException {
                    ResultSet resultSet = null;
                    try (PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {
                        preparedStatement.setInt(1, id);
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        return daoUtil.resultSetAsObject(resultSet);
                    }
                    finally {
                        closeResultSet(resultSet);
                    }
                }
            });
    }

    protected void update(DaoUtil daoUtil, T entity, int id, String updateQuery, String error) {
            ExecuteDAO.execute(error, new Executable() {
                @Override
                public Object exec(Connection connection) throws SQLException {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        int lastIndex = daoUtil.setPreparedStatement(preparedStatement, entity);
                        preparedStatement.setInt(lastIndex, id);
                        preparedStatement.execute();
                        connection.commit();
                        return true;
                    }
                }
            });
        }

}
