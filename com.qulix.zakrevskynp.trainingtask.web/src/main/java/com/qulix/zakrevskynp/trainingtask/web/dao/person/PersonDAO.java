package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.dao.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * CRUD operations for person's
 * @author Q-NZA
 */
public interface PersonDAO {

    /**
     * Gets list of persons
     *
     * @return list of all persons from database
     * @throws DAOException
     */
    List<Person> getPersonsList() throws DAOException;

    /**
     * Inserts new person in database
     * @param parameters Person object inserted in database
     * @throws DAOException
     */
    void addPerson(Map<String, Object> parameters) throws DAOException;

    /**
     * Remove person from database by id
     * @param id person's id
     * @throws DAOException
     */
    void removePerson(int id) throws DAOException;

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     * @throws DAOException
     */
    Person getPersonById(int id) throws DAOException;

    /**
     * Update information about exist person
     *
     * @param parameters Person object
     * @throws DAOException
     */
    void updatePerson(Map<String, Object> parameters) throws DAOException;
}
