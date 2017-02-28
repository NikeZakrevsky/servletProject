package com.qulix.zakrevskynp.trainingtask.dao.person;

import com.qulix.zakrevskynp.trainingtask.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.model.Person;

import java.util.List;

/**
 * CRUD operations for person's
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
     * @param fname person's name
     * @param sname person's middle name
     * @param lname person's last name
     * @param position person's position
     * @throws DAOException
     */
    void addPerson(String fname, String sname, String lname, String position) throws DAOException;

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
     * @param id person's id
     * @param fname person's name
     * @param sname person's middle name
     * @param lname person's last name
     * @param position person's position
     * @throws DAOException
     */
    void updatePerson(int id, String fname, String sname, String lname, String position) throws DAOException;
}
