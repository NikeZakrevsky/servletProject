package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.*;
import java.util.List;

/**
 * @author Q-NZA
 * @param <T>
 */
public class AbstractDAO<T> {
    private final Class<T> typeParameterClass;
    
    public AbstractDAO(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    protected List getList(DaoUtil daoUtil, String selectQuery, String error) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            return daoUtil.resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

    }

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                //
            }
        }
    }

    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                //
            }
        }
    }


    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                //
            }
        }
    }

    protected void remove(int id, String removeQuery, String error)  {
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
            throw new DAOException(e);
        }
        finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    protected void add(DaoUtil daoUtil, T entity, String insertQuery, String error)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(insertQuery);
            daoUtil.setPreparedStatement(preparedStatement, entity);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    protected T getById(DaoUtil daoUtil, int id, String getByIdQuery, String error)  {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(getByIdQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return typeParameterClass.cast(daoUtil.resultSetAsObject(resultSet));
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }


    protected void update(DaoUtil daoUtil, T entity, int id, String updateQuery, String error) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(updateQuery);
            int lastIndex = daoUtil.setPreparedStatement(preparedStatement, entity);
            preparedStatement.setInt(lastIndex, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

}
