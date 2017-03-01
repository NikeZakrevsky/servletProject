package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utilities for person's dao layer
 */
public class PersonUtil {
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
    public Person resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt(ID), resultSet.getString(FNAME), resultSet.getString(SNAME),
                resultSet.getString(LNAME), resultSet.getString(POSITION));
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param person Person object
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, Person person) throws SQLException {
        preparedStatement.setString(1, person.getFname());
        preparedStatement.setString(2, person.getSname());
        preparedStatement.setString(3, person.getLname());
        preparedStatement.setString(4, person.getPosition());
    }
}
