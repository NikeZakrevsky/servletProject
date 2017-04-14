package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.dao.ExecuteDAO;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Implementation of {@link PersonDAO} interface
 * @author Q-NZA
 */
public class PersonDAOImpl implements PersonDAO {
    
    private static final String SELECT_QUERY = "select id, fname, sname, lname, position from persons";
    private static final String INSERT_QUERY = "insert into persons(fname, sname, lname, position) values (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from persons where id=?";
    private static final String SELECT_BY_ID_QUERY = "select id, fname, sname, lname, position from persons where id=?";
    private static final String UPDATE_QUERY = "update persons set fname = ?, sname = ?, lname = ?, position = ? where id = ?";

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
    @SuppressWarnings("unchecked")
    public List<Person> getPersonsList()  {
        return (List<Person>) ExecuteDAO.execute(GET_PERSONS_LIST_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return personUtil.resultSetToList(resultSet);
                }
            });
    }

    /**
     * Inserts new person in database
     * @param person data from add person form
     */
    public void addPerson(Person person)  {
        ExecuteDAO.execute(ADD_PERSON_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(INSERT_QUERY)) {
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
    public void removePerson(int id)  {
        ExecuteDAO.execute(REMOVE_PERSON_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
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
        return (Person) ExecuteDAO.execute(GET_PERSON_BY_ID_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
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
     * @param person Person object
     */
    public void updatePerson(Person person) {
        ExecuteDAO.execute(UPDATE_PERSON_ERROR, (connection) -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
                    personUtil.setPreparedStatement(preparedStatement, person);
                    preparedStatement.setInt(5, person.getId());
                    preparedStatement.execute();
                    connection.commit();
                    return true;
                }
            });
    }
}
