package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.AbstractDAO;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Implementation of {@link PersonDAO} interface
 * @author Q-NZA
 */
public class PersonDAOImpl extends AbstractDAO<Person> implements PersonDAO {
    
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

    private static final String ID = "id";
    private static final String FIRST_NAME = "fname";
    private static final String MIDDLE_NAME = "sname";
    private static final String LAST_NAME = "lname";
    private static final String POSITION = "position";

    /**
     * Gets list of persons
     *
     * @return list of all persons from database
     */
    @Override
    public List getPersonsList()  {
        return super.getList(SELECT_QUERY, GET_PERSONS_LIST_ERROR);
    }

    /**
     * Inserts new person in database
     * @param person data from add person form
     */
    @Override
    public void addPerson(Person person)  {
        super.add(person, INSERT_QUERY, ADD_PERSON_ERROR);
    }

    /**
     * Remove person from database by id
     * @param id person's id
     */
    @Override
    public void removePerson(int id)  {
        super.remove(id, DELETE_QUERY, REMOVE_PERSON_ERROR);
    }

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     */
    @Override
    public Person getPersonById(int id) {
        return super.getById(id, SELECT_BY_ID_QUERY, GET_PERSON_BY_ID_ERROR);
    }

    /**
     * Update information about exist person
     *
     * @param person Person object
     */
    @Override
    public void updatePerson(Person person) {
        super.update(person, person.getId(), UPDATE_QUERY, UPDATE_PERSON_ERROR);
    }

    /**
     * Create object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created person object
     * @throws SQLException
     */
    public Person resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String firstName = resultSet.getString(FIRST_NAME);
        String middleName = resultSet.getString(MIDDLE_NAME);
        String lastName = resultSet.getString(LAST_NAME);
        String position = resultSet.getString(POSITION);

        return new Person(id, firstName, middleName, lastName, position);
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    public List<Person> resultSetToList(ResultSet rs) throws SQLException {
        List<Person> persons = new ArrayList<>();
        while (rs.next()) {
            persons.add(resultSetAsObject(rs));
        }
        return persons;
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param person Person object
     * @throws SQLException
     */
    public int setPreparedStatement(PreparedStatement preparedStatement, Person person) throws SQLException {
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getMiddleName());
        preparedStatement.setString(3, person.getLastName());
        preparedStatement.setString(4, person.getPosition());
        return 5;
    }
}
