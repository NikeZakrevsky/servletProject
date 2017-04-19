package com.qulix.zakrevskynp.trainingtask.web.dao.person;


import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Interface describing the operation on @{{@link Person}} of the DAO pattern
 * @author Q-NZA
 */
public interface PersonDAO {

    List getPersonsList();

    void addPerson(Person person);

    void removePerson(int id);

    Person getPersonById(int id);

    void updatePerson(Person person);
}
