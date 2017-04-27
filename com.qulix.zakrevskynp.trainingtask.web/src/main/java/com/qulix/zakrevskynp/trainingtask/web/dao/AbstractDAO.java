package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.List;

/**
 * Abstract class with shared methods for DAOs
 *
 * @param <T>
 * @author Q-NZA
 */
abstract class AbstractDAO<T> implements IDao<T> {

    /**
     * Reading of the result error message
     */
    static final String RESULT_SET_ERROR = "Ошибка при чтении ResultSet-a";

    /**
     * Creating prepared statement error message
     */
    static final String PREPARED_STATEMENT_ERROR = "Ошибка при создании PreparedStatement-а";


    /**
     * Gets all entities
     *
     * @param selectQuery sql query for selecting entities
     * @param error error message
     * @return list of entities
     */
    List<T> getAll(String selectQuery, String error) {
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

    /**
     * Removes entity
     *
     * @param id id of entity
     * @param removeQuery sql query for removing entity
     * @param error error message
     */
    void remove(int id, String removeQuery, String error)  {
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

    /**
     * Adds entity
     *
     * @param entity entity for adding
     * @param insertQuery sql query for inserting entity
     * @param error error message
     */
    void add(T entity, String insertQuery, String error) {
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

    /**
     * Gets entity by id
     *
     * @param id id of entity
     * @param getByIdQuery sql query for getting entity by id
     * @param error error message
     * @return entity
     */
    T getById(int id, String getByIdQuery, String error)  {
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

    /**
     * Updates entity
     *
     * @param entity entity for updating
     * @param id id of entity
     * @param updateQuery sql query for updating entity
     * @param error error message
     */
    void update(T entity, int id, String updateQuery, String error) {
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
     *
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    abstract T resultSetAsObject(ResultSet resultSet) throws SQLException;

    /**
     * Convert the ResultSet to a List of objects
     *
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    abstract List<T> resultSetToList(ResultSet rs) throws SQLException;

    /**
     * Set parameters to prepared statement
     *
     * @param preparedStatement link of the prepared statement for setting parameters
     * @throws SQLException throws while setting parameters to prepared statement
     */
    abstract int setPreparedStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    /**
     * Close result set
     *
     * @param resultSet result set for closing
     */
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

    /**
     * Close statement
     *
     * @param statement statement closing
     */
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

    /**
     * Close connection
     *
     * @param connection connection for closing
     */
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
