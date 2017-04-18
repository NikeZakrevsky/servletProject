package com.qulix.zakrevskynp.trainingtask.web.dao.person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.DaoUtil;
import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Utilities for person's dao layer
 * @author Q-NZA
 */
public class PersonUtil  implements DaoUtil<Person>  {
    private static final String ID = "id";
    private static final String FIRST_NAME = "fname";
    private static final String MIDDLE_NAME = "sname";
    private static final String LAST_NAME = "lname";
    private static final String POSITION = "position";

    /**
     * Create object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created person object
     * @throws SQLException
     */
    public Person resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String firstName = resultSet.getString(FIRST_NAME);
        String middleName = resultSet.getString(MIDDLE_NAME);
        String lastName = resultSet.getString(LAST_NAME);
        String position = resultSet.getString(POSITION);

        return new Person(id, firstName, middleName, lastName, position);
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    public List<Person> resultSetToList(ResultSet rs) throws SQLException {
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
    public int setPreparedStatement(PreparedStatement preparedStatement, Person person) throws SQLException {
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getMiddleName());
        preparedStatement.setString(3, person.getLastName());
        preparedStatement.setString(4, person.getPosition());
        return 5;
    }
}
