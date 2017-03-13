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
     * @throws CustomException
     */
    List<Person> getPersonsList() throws CustomException;

    /**
     * Inserts new person in database
     * @param parameters person form data
     * @throws CustomException
     */
    boolean addPerson(Map<String, Object> parameters) throws CustomException;

    /**
     * Remove person from database by id
     * @param id person's id
     * @throws CustomException
     */
    boolean removePerson(int id) throws CustomException;

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     * @throws CustomException
     */
    Person getPersonById(int id) throws CustomException;

    /**
     * Update information about exist person
     *
     * @param parameters person form data
     * @throws CustomException
     */
    boolean updatePerson(Map<String, Object> parameters) throws CustomException;
}