package com.qulix.zakrevskynp.trainingtask.web.person.dao;

import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;

/**
 * CRUD operations for person's
 * @author Q-NZA
 */
public interface PersonDAO {

    /**
     * Gets list of persons
     *
     * @return list of all persons from database
     * @
     */
    List<Person> getPersonsList() ;

    /**
     * Inserts new person in database
     * @param parameters person form data
     * @
     */
    boolean addPerson(Map<String, Object> parameters) ;

    /**
     * Remove person from database by id
     * @param id person's id
     * @
     */
    boolean removePerson(int id) ;

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     * @
     */
    Person getPersonById(int id) ;

    /**
     * Update information about exist person
     *
     * @param parameters person form data
     * @
     */
    boolean updatePerson(Map<String, Object> parameters) ;
}
