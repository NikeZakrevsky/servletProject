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
public class PersonDaoImpl extends AbstractDao<Person> {
    
    private static final String SELECT_QUERY = "select person_id, first_name, middle_name, last_name, position from persons";
    private static final String INSERT_QUERY = "insert into persons(first_name, middle_name, last_name,position) values(?,?,?,?)";
    private static final String DELETE_QUERY = "delete from persons where person_id=?";
    private static final String SELECT_PERSON = "select person_id, first_name, middle_name, last_name, position from persons " +
        "where person_id=?";
    private static final String UPDATE_QUERY = "update persons set first_name = ?, middle_name = ?, last_name = ?,position = ? " +
        "where person_id = ?";
    private static final String ADD_PERSON_ERROR = "Ошибка при добавлении исполнителя";
    private static final String REMOVE_PERSON_ERROR = "Ошибка при удалении исполнителя";
    private static final String GET_PERSONS_ERROR = "Ошибка при получении списка исполнителей";
    private static final String GET_PERSON_ERROR = "Ошибка при получении исполнителя";
    private static final String UPDATE_PERSON_ERROR = "Ошибка при обновлении исполнителя";
    private static final String ID = "person_id";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String LAST_NAME = "last_name";
    private static final String POSITION = "position";

    /**
     * Getting a list of persons
     *
     * @return list of all persons from the database
     */
    @Override
    public List<Person> getAll()  {
        return super.getAll(SELECT_QUERY, GET_PERSONS_ERROR);
    }

    /**
     * Inserting a new person in the database
     *
     * @param person person data from the form
     */
    @Override
    public void add(Person person)  {
        super.add(person, INSERT_QUERY, ADD_PERSON_ERROR, person.getFirstName(), person.getMiddleName(), person.getLastName(),
            person.getPosition());
    }

    /**
     * Removing a person from the database by id
     *
     * @param id person's id
     */
    @Override
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY, REMOVE_PERSON_ERROR);
    }

    /**
     * Getting a person by id
     *
     * @param id person's id
     * @return @{{@link Person}}
     */
    @Override
    public Person get(int id) {
        return super.get(id, SELECT_PERSON, GET_PERSON_ERROR);
    }

    /**
     * Updating information about a person
     *
     * @param person Person object
     */
    @Override
    public void update(Person person) {
        super.update(UPDATE_QUERY, UPDATE_PERSON_ERROR, person.getFirstName(), person.getMiddleName(), person.getLastName(),
            person.getPosition(), person.getId());
    }

    /**
     * Creating object from the ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created person object
     */
    @Override
    public Person resultSetAsObject(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt(ID);
            String firstName = resultSet.getString(FIRST_NAME);
            String middleName = resultSet.getString(MIDDLE_NAME);
            String lastName = resultSet.getString(LAST_NAME);
            String position = resultSet.getString(POSITION);
            return new Person(id, firstName, middleName, lastName, position);
        }
        catch (SQLException e) {
            throw new DaoException(RESULT_SET_ERROR, e);
        }
    }

    /**
     * Converting the ResultSet to a list of objects
     *
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     */
    @Override
    public List<Person> resultSetToList(ResultSet rs) {
        try {
            List<Person> persons = new ArrayList<>();
            while (rs.next()) {
                persons.add(resultSetAsObject(rs));
            }
            return persons;
        }
        catch (SQLException e) {
            throw new DaoException(RESULT_SET_ERROR, e);
        }
    }

}
