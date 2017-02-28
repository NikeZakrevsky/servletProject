package com.qulix.trainingtask.dao.person;

import com.qulix.trainingtask.model.Person;

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
     * @param resultSet
     * @return created person object
     * @throws SQLException
     */
    public Person resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt(ID), resultSet.getString(FNAME), resultSet.getString(SNAME), resultSet.getString(LNAME), resultSet.getString(POSITION));
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement prepared statement for setting parameters
     * @param fname person's first name
     * @param sname person's middle name
     * @param lname person's last name
     * @param position person's position
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, String fname, String sname, String lname, String position) throws SQLException {
        preparedStatement.setString(1, fname);
        preparedStatement.setString(2, sname);
        preparedStatement.setString(3, lname);
        preparedStatement.setString(4, position);
    }
}
