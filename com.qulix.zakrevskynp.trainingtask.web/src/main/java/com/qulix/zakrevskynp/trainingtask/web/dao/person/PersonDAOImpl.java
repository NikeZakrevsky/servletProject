package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import com.qulix.zakrevskynp.trainingtask.web.dao.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of {@link PersonDAO} interface
 */
public class PersonDAOImpl implements PersonDAO {
    
    private static final String SELECT_QUERY = "SELECT id, fname, sname, lname, position FROM persons";
    private static final String INSERT_QUERY = "insert into persons(fname, sname, lname, position) values (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from persons where id=?";
    private static final String SELECT_BY_ID_QUERY = "select id, fname, sname, lname, position FROM persons where id=?";
    private static final String UPDATE_QUERY = "update persons set fname = ?, sname = ?, lname = ?, position = ? where id = ?";

    private PersonUtil personUtil = new PersonUtil();
    private Logger logger = Logger.getLogger(PersonDAOImpl.class.getName());

    /**
     * Gets list of persons
     *
     * @return list of all persons from database
     * @throws DAOException
     */
    public List<Person> getPersonsList() throws DAOException {
        List<Person> persons = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(personUtil.resultSetAsObject(resultSet));
            }
            return persons;
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Inserts new person in database
     * @param fname person's name
     * @param sname person's middle name
     * @param lname person's last name
     * @param position person's position
     * @throws DAOException
     */
    public void addPerson(String fname, String sname, String lname, String position) throws DAOException {
        try (
                Connection connection =  ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(INSERT_QUERY)
        ) {
            personUtil.setPreparedStatement(preparedStatement, fname, sname, lname, position);

            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Remove person from database by id
     * @param id person's id
     * @throws DAOException
     */
    public void removePerson(int id) throws DAOException {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     * @throws DAOException
     */
    public Person getPersonById(int id) throws DAOException {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Person(resultSet.getInt("id"), resultSet.getString("fname"), resultSet.getString("sname"), resultSet.getString("lname"), resultSet.getString("position"));
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Update information about exist person
     *
     * @param id person's id
     * @param fname person's name
     * @param sname person's middle name
     * @param lname person's last name
     * @param position person's position
     * @throws DAOException
     */
    public void updatePerson(int id, String fname, String sname, String lname, String position) throws DAOException {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            personUtil.setPreparedStatement(preparedStatement, fname, sname, lname, position);
            preparedStatement.setInt(5, id);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }
}
