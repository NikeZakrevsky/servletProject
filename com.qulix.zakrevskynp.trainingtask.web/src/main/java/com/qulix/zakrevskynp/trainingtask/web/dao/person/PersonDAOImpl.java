package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.AbstractDAO;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Implementation of {@link PersonDAO} interface
 * @author Q-NZA
 */
public class PersonDAOImpl  extends AbstractDAO<Person> implements PersonDAO {
    
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
    @Override
    public List<Person> getPersonsList()  {
        return super.getList(personUtil, SELECT_QUERY, GET_PERSONS_LIST_ERROR);
    }

    /**
     * Inserts new person in database
     * @param person data from add person form
     */
    @Override
    public void addPerson(Person person)  {
        super.add(personUtil, person, INSERT_QUERY, ADD_PERSON_ERROR);
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
    public Person getPersonById(int id)  {
        return super.getById(personUtil, id, SELECT_BY_ID_QUERY, GET_PERSON_BY_ID_ERROR);
    }

    /**
     * Update information about exist person
     *
     * @param person Person object
     */
    @Override
    public void updatePerson(Person person) {
        super.update(personUtil, person, person.getId(), UPDATE_QUERY, UPDATE_PERSON_ERROR);
    }
}
