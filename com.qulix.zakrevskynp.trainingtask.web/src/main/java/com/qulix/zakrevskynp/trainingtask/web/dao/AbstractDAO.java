package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.BaseDAOEntity;

/**
 * @author Q-NZA
 * @param <T>
 */
public abstract class AbstractDAO<T extends BaseDAOEntity> implements IDao<T> {

    static final String RESULT_SET_ERROR = "Ошибка при чтении ResultSet-a";
    static final String PREPARED_STATEMENT_ERROR = "Ошибка при создании PreparedStatement-а";

    static {
        ConnectionFactory.readDatabaseProperty();
    }

    public List<T> getAll(String selectQuery, String error) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            return resultSetToList(resultSet);
        }
        catch (SQLException e) {
            throw new DAOException(error, e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

    }

    public void remove(int id, String removeQuery, String error)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new DAOException(error, e);
        }
        finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void add(T entity, String insertQuery, String error) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(insertQuery);
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new DAOException(error, e);
        }
        finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public T getById(int id, String getByIdQuery, String error)  {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(getByIdQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSetAsObject(resultSet);
        }
        catch (SQLException e) {
            throw new DAOException(error, e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void update(T entity, int id, String updateQuery, String error) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(updateQuery);
            int lastIndex = setPreparedStatement(preparedStatement, entity);
            preparedStatement.setInt(lastIndex, id);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new DAOException(error, e);
        }
        finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    /**
     * Create Task object from ResultSet
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    protected abstract T resultSetAsObject(ResultSet resultSet) throws SQLException;

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    protected abstract List<T> resultSetToList(ResultSet rs) throws SQLException;

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @throws SQLException throws while setting parameters to prepared statement
     */
    protected abstract int setPreparedStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                //
            }
        }
    }

    void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            }
            catch (SQLException e) {
                //
            }
        }
    }

    void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                //
            }
        }
    }
}
