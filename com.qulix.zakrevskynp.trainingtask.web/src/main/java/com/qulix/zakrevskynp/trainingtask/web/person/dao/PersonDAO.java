package com.qulix.zakrevskynp.trainingtask.web.person.dao;


import java.util.List;

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
    List<Person> getPersonsList();

    /**
     * Inserts new person in database
     * @param person person form data
     * @
     */
    void addPerson(Person person);

    /**
     * Remove person from database by id
     * @param id person's id
     * @
     */
    void removePerson(int id);

    /**
     * Gets person by id
     *
     * @param id person's id
     * @return person by id
     * @
     */
    Person getPersonById(int id);

    /**
     * Update information about exist person
     *
     * @param person person form data
     * @
     */
    void updatePerson(Person person);
}
