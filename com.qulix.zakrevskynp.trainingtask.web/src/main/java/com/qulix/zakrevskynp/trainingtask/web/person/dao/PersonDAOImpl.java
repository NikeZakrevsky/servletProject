package com.qulix.zakrevskynp.trainingtask.web.person.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.Executable;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;

/**
 * Implementation of {@link PersonDAO} interface
 * @author Q-NZA
 */
public class PersonDAOImpl implements PersonDAO {
    
    private static final String SELECT_QUERY = "SELECT \"id\", \"fname\", \"sname\", \"lname\", " +
            "\"position\" FROM \"persons\"";
    private static final String INSERT_QUERY = "insert into \"persons\"(\"fname\", \"sname\", \"lname\", " +
            "\"position\") values (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from \"persons\" where \"id\"=?";
    private static final String SELECT_BY_ID_QUERY = "select \"id\", \"fname\", \"sname\", \"lname\", " +
            "\"position\" FROM \"persons\" where \"id\"=?";
    private static final String UPDATE_QUERY = "update \"persons\" set \"fname\" = ?, \"sname\" = ?, " +
            "\"lname\" = ?, \"position\" = ? where \"id\" = ?";

    private static final String ADD_PERSON_ERROR = "Error while adding person";
    private static final String REMOVE_PERSON_ERROR = "Error while deleting person";
    private static final String GET_PERSONS_LIST_ERROR = "Error while getting persons list";
    private static final String GET_PERSON_BY_ID_ERROR = "Error while getting person";
    private static final String UPDATE_PERSON_ERROR = "Error while updating person";

    private PersonUtil personUtil = new PersonUtil();

    /**
     * Gets list of persons
     *
     * @return list of all persons from database
     */
    public List<Person> getPersonsList()  {
        return (List<Person>) execute(GET_PERSONS_LIST_ERROR, () -> {
            try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            ) {
                List<Person> persons = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    persons.add(personUtil.resultSetAsObject(resultSet));
                }
                return persons;
            }
        });
    }

    /**
     * Inserts new person in database
     * @param parameters data from add person form
     */
    public boolean addPerson(Person person)  {
        return (boolean) execute(ADD_PERSON_ERROR, () -> {
            try (
                Connection connection =  ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(INSERT_QUERY)
            ) {
                personUtil.setPreparedStatement(preparedStatement, person);
                preparedStatement.execute();
                connection.commit();
                return true;
            }
        });
    }

    /**
     * Remove person from database by id
     * @param id person's id
     */
    public boolean removePerson(int id)  {
        return (boolean) execute(REMOVE_PERSON_ERROR, () -> {
            try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
            ) {
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                connection.commit();
                return true;
            }
        });
    }

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     */
    public Person getPersonById(int id)  {
        return (Person)execute(GET_PERSON_BY_ID_ERROR, () -> {
            try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)
            ) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return personUtil.resultSetAsObject(resultSet);
            }
        });
    }

    /**
     * Update information about exist person
     *
     * @param parameters Person object
     */
    public boolean updatePerson(Person person) {
        return (boolean) execute(UPDATE_PERSON_ERROR, () -> {
            try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
            ) {
                personUtil.setPreparedStatement(preparedStatement, person);
                preparedStatement.setInt(5, person.getId());
                preparedStatement.execute();
                connection.commit();
                return true;
            }
        });
    }

    private Object execute(String message, Executable ex) {
        try {
            return ex.exec();
        } catch (SQLException e) {
            throw new RuntimeException(message, e);
        }
    }
}
