package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Using DAO pattern for operations with @{{@link Person}} objects
 *
 * @author Q-NZA
 */
public class PersonDao extends AbstractDao<Person> {
    
    private static final String SELECT_QUERY = "select person_id, first_name, middle_name, last_name, position from persons";
    private static final String INSERT_QUERY = "insert into persons(first_name, middle_name, last_name,position) values(?,?,?,?)";
    private static final String DELETE_QUERY = "delete from persons where person_id=?";
    private static final String SELECT_PERSON = "select person_id, first_name, middle_name, last_name, position from persons " +
        "where person_id=?";
    private static final String UPDATE_QUERY = "update persons set first_name = ?, middle_name = ?, last_name = ?,position = ? " +
        "where person_id = ?";

    private static final String ID = "person_id";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String LAST_NAME = "last_name";
    private static final String POSITION = "position";

    /**
     * Gets a list of persons
     *
     * @return list of all persons from the database
     */
    @Override
    public List<Person> getAll()  {
        return super.getAll(SELECT_QUERY);
    }

    /**
     * Inserts a new person in the database
     *
     * @param person person data from the form
     */
    @Override
    public void add(Person person)  {
        super.add(INSERT_QUERY, person.getFirstName(), person.getMiddleName(), person.getLastName(),
            person.getPosition());
    }

    /**
     * Removes a person from the database by id
     *
     * @param id person's id
     */
    @Override
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY);
    }

    /**
     * Gets a person by id
     *
     * @param id person's id
     * @return @{{@link Person}}
     */
    @Override
    public Person get(int id) {
        return super.get(id, SELECT_PERSON);
    }

    /**
     * Updates information about a person
     *
     * @param person Person object
     */
    @Override
    public void update(Person person) {
        super.update(UPDATE_QUERY, person.getFirstName(), person.getMiddleName(), person.getLastName(),
            person.getPosition(), person.getId());
    }

    /**
     * Creats object from the ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created person object
     */
    protected Person resultSetAsObject(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt(ID);
            String firstName = resultSet.getString(FIRST_NAME);
            String middleName = resultSet.getString(MIDDLE_NAME);
            String lastName = resultSet.getString(LAST_NAME);
            String position = resultSet.getString(POSITION);
            return new Person(id, firstName, middleName, lastName, position);
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Converts the ResultSet to a list of objects
     *
     * @param resultSet @{{@link ResultSet}} object converted to list
     * @return tasks list
     */
    @Override
    protected List<Person> resultSetToList(ResultSet resultSet) {
        try {
            List<Person> persons = new ArrayList<>();
            while (resultSet.next()) {
                persons.add(resultSetAsObject(resultSet));
            }
            return persons;
        }
        catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
