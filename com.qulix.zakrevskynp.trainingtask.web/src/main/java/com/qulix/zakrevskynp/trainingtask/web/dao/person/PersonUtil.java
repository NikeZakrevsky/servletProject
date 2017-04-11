package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Utilities for person's dao layer
 * @author Q-NZA
 */
class PersonUtil {
    private static final String ID = "id";
    private static final String FNAME = "fname";
    private static final String SNAME = "sname";
    private static final String LNAME = "lname";
    private static final String POSITION = "position";

    /**
     * Create object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created person object
     * @throws SQLException
     */
    Person resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt(ID), resultSet.getString(FNAME), resultSet.getString(SNAME),
                resultSet.getString(LNAME), resultSet.getString(POSITION));
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    List<Person> resultSetToList(ResultSet rs) throws SQLException {
    	List<Person> persons = new ArrayList<>();
    	while (rs.next()) {
	        persons.add(resultSetAsObject(rs));
        }
        return persons;
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param person Person object
     * @throws SQLException
     */
    void setPreparedStatement(PreparedStatement preparedStatement, Person person) throws SQLException {
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getMiddleName());
        preparedStatement.setString(3, person.getLastName());
        preparedStatement.setString(4, person.getPosition());
    }
}
