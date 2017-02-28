package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utilities for project'd dao layer
 */
public class ProjectUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "shortname";
    private static final String DESCRIPTION = "description";

    /**
     * Create object from ResutSet
     *
     * @param resultSet resultSet for converting to object
     * @return created project object
     * @throws SQLException
     */
    public Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Project(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(SHORTNAME), resultSet.getString(DESCRIPTION));
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param name project's name
     * @param shortName project's short name
     * @param description project's description
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, String name, String shortName, String description)  throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, shortName);
        preparedStatement.setString(3, description);
    }

}
