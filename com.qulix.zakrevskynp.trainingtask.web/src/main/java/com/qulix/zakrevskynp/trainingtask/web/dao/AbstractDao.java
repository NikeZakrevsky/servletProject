package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.BaseDaoEntity;

/**
 * Abstract class with shared methods for DAOs
 *
 * @param <T>
 * @author Q-NZA
 */
abstract class AbstractDao<T extends BaseDaoEntity> implements IDao<T> {

    /**
     * Gets all entities
     *
     * @param query sql query for selecting entities
     * @return list of entities
     */
    protected List<T> getAll(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return resultSetToList(resultSet);
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

    }

    /**
     * Deletion of an entity
     *
     * @param id id of entity
     * @param query sql query for removing entity
     */
    protected void remove(int id, String query)  {
        executeQuery(query, id);
    }

    /**
     * Adds an entity
     *
     * @param query sql query for inserting entity
     */
    protected void add(String query, Object... parameters) {
        executeQuery(query, parameters);
    }

    private void executeQuery(String query, Object... parameters) throws DaoException {
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
            throw new DaoException(e);
        }
        finally {
            closeConnection(connection);
            closeStatement(preparedStatement);
        }
    }

    /**
     * Gets the entity by identifier
     *
     * @param id id of entity
     * @param query sql query for getting entity by id
     * @return entity
     */
    protected T get(int id, String query)  {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            setPreparedStatement(preparedStatement, id);
            resultSet = preparedStatement.executeQuery();
            List<T> resultList = resultSetToList(resultSet);
            if (resultList.size() > 0) {
                return resultList.get(0);
            }
            return null;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    /**
     * Updates an entity
     *
     * @param query sql query for updating entity
     */
    protected void update(String query, Object... parameters) {
        executeQuery(query, parameters);
    }

    /**
     * Converts of the ResultSet to a List of objects
     *
     * @param resultSet @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    protected abstract List<T> resultSetToList(ResultSet resultSet) throws SQLException;

    /**
     * Sets parameters to prepared statement
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
            throw new DaoException(e);
        }
    }

    /**
     * Closes result set
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
     * Closes statement
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
     * Closes connection
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
