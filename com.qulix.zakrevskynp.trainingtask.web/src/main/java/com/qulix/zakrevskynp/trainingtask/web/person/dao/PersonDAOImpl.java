package com.qulix.zakrevskynp.trainingtask.web.person.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;

/**
 * Implementation of {@link PersonDAO} interface
 * @author Q-NZA
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
     * @throws CustomException
     */
    public List<Person> getPersonsList() throws CustomException {
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
            throw new CustomException("Error while getting persons list", e);
        }
    }

    /**
     * Inserts new person in database
     * @param parameters data from add person form
     * @throws CustomException
     */
    public void addPerson(Map<String, Object> parameters) throws CustomException {
        try (
                Connection connection =  ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(INSERT_QUERY)
        ) {
            personUtil.setPreparedStatement(preparedStatement, parameters);

            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CustomException("Error while adding person", e);
        }

    }

    /**
     * Remove person from database by id
     * @param id person's id
     * @throws CustomException
     */
    public void removePerson(int id) throws CustomException {
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
            throw new CustomException("Error while deleting person", e);
        }
    }

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     * @throws CustomException
     */
    public Person getPersonById(int id) throws CustomException {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Person(resultSet.getInt("id"), resultSet.getString("fname"), resultSet.getString("sname"),
                    resultSet.getString("lname"), resultSet.getString("position"));
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CustomException("Error while getting person", e);
        }
    }

    /**
     * Update information about exist person
     *
     * @param parameters Person object
     * @throws CustomException
     */
    public void updatePerson(Map<String, Object> parameters) throws CustomException {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            personUtil.setPreparedStatement(preparedStatement, parameters);
            preparedStatement.setInt(5, Integer.parseInt(parameters.get("id").toString()));
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new CustomException("Error while updating person", e);
        }
    }
}                         
