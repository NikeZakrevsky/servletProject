package com.qulix.zakrevskynp.trainingtask.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Using DAO pattern for operations with @{{@link Person}} objects
 * @author Q-NZA
 */
public class PersonDAOImpl extends AbstractDAO<Person> {
    
    private static final String SELECT_QUERY = "select id, first_name, middle_name, last_name, position from persons";
    private static final String INSERT_QUERY = "insert into persons(first_name, middle_name, last_name, position) values (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from persons where id=?";
    private static final String SELECT_BY_ID_QUERY = "select id, first_name, middle_name, last_name, position from persons where id=?";
    private static final String UPDATE_QUERY = "update persons set first_name = ?, middle_name = ?, last_name = ?, position = ? where id = ?";

    private static final String ADD_PERSON_ERROR = "Ошибка при добавлении исполнителя";
    private static final String REMOVE_PERSON_ERROR = "Ошибка при удалении исполнителя";
    private static final String GET_PERSONS_LIST_ERROR = "Ошибка при получении списка исполнителей";
    private static final String GET_PERSON_BY_ID_ERROR = "Ошибка при получении исполнителя";
    private static final String UPDATE_PERSON_ERROR = "Ошибка при обновлении исполнителя";

    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String LAST_NAME = "last_name";
    private static final String POSITION = "position";

    /**
     * Getting a list of persons
     * @return list of all persons from the database
     */
    public List<Person> getAll()  {
        return super.getAll(SELECT_QUERY, GET_PERSONS_LIST_ERROR);
    }

    /**
     * Inserting a new person in the database
     * @param person person data from the form
     */
    public void add(Person person)  {
        super.add(person, INSERT_QUERY, ADD_PERSON_ERROR);
    }

    /**
     * Removing a person from the database by id
     * @param id person's id
     */
    public void remove(int id)  {
        super.remove(id, DELETE_QUERY, REMOVE_PERSON_ERROR);
    }

    /**
     * Getting a person by id
     * @param id person's id
     * @return @{{@link Person}}
     */
    public Person getById(int id) {
        return super.getById(id, SELECT_BY_ID_QUERY, GET_PERSON_BY_ID_ERROR);
    }

    /**
     * Updating information about a person
     *
     * @param person Person object
     */
    public void update(Person person) {
        super.update(person, person.getId(), UPDATE_QUERY, UPDATE_PERSON_ERROR);
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
            throw new DAOException(RESULT_SET_ERROR, e);
        }
    }

    /**
     * Converting the ResultSet to a list of objects
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
            throw new DAOException(RESULT_SET_ERROR, e);
        }
    }

    /**
     * Setting parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param person Person object
     */
    @Override
    public int setPreparedStatement(PreparedStatement preparedStatement, Person person) {
        try {
            int i = 0;
            preparedStatement.setString(++i, person.getFirstName());
            preparedStatement.setString(++i, person.getMiddleName());
            preparedStatement.setString(++i, person.getLastName());
            preparedStatement.setString(++i, person.getPosition());
            return ++i;
        }
        catch (SQLException e) {
            throw new DAOException(PREPARED_STATEMENT_ERROR, e);
        }
    }
}
