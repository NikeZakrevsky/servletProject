package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.BaseDAOEntity;

/**
 * Abstract class with shared methods for DAOs
 *
 * @param <T>
 * @author Q-NZA
 */
abstract class AbstractDao<T extends BaseDAOEntity> implements IDao<T> {

    /**
     * Reading of the result error message
     */
    static final String RESULT_SET_ERROR = "Ошибка при чтении ResultSet-a";

    /**
     * Creating prepared statement error message
     */
    private static final String PREPARED_STATEMENT_ERROR = "Ошибка при создании PreparedStatement-а";


    /**
     * Gets all entities
     *
     * @param selectQuery sql query for selecting entities
     * @param error error message
     * @return list of entities
     */
    protected List<T> getAll(String selectQuery, String error) {
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
            throw new DaoException(error, e);
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
    protected void remove(int id, String removeQuery, String error)  {
        executeQuery(removeQuery, error, id);
    }

    /**
     * Adds entity
     *
     * @param entity entity for adding
     * @param insertQuery sql query for inserting entity
     * @param error error message
     */
    protected void add(T entity, String insertQuery, String error, Object... parameters) {
        executeQuery(insertQuery, error, parameters);
    }

    private void executeQuery(String query, String error, Object... parameters) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            setPreparedStatement(preparedStatement, parameters);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new DaoException(error, e);
        }
        finally {
            closeConnection(connection);
            closeStatement(preparedStatement);
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
    protected T getById(int id, String getByIdQuery, String error)  {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(getByIdQuery);
            setPreparedStatement(preparedStatement, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSetAsObject(resultSet);
        }
        catch (SQLException e) {
            throw new DaoException(error, e);
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
     * @param updateQuery sql query for updating entity
     * @param error error message
     */
    protected void update(String updateQuery, String error, Object... parameters) {
        executeQuery(updateQuery, error, parameters);
    }

    /**
     * Create Task object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    protected abstract T resultSetAsObject(ResultSet resultSet) throws SQLException;

    /**
     * Convert the ResultSet to a List of objects
     *
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    protected abstract List<T> resultSetToList(ResultSet rs) throws SQLException;

    /**
     * Set parameters to prepared statement
     *
     * @param preparedStatement link of the prepared statement for setting parameters
     */
    protected void setPreparedStatement(PreparedStatement preparedStatement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                Object param = parameters[i];
                preparedStatement.setObject(i + 1, param);
            }
        }
        catch (SQLException e) {
            throw new DaoException(PREPARED_STATEMENT_ERROR, e);
        }
    }

    /**
     * Close result set
     *
     * @param resultSet result set for closing
     */
    protected void closeResultSet(ResultSet resultSet) {
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
    protected void closeStatement(Statement statement) {
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
    protected void closeConnection(Connection connection) {
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
