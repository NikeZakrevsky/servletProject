package com.qulix.zakrevskynp.trainingtask.web.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.project.Project;

/**
 * Utilities for project'd dao layer
 * @author Q-NZA
 */
public class ProjectUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * Create object from ResutSet
     *
     * @param resultSet resultSet for converting to object
     * @return created project object
     * @throws SQLException
     */
    public Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Project(resultSet.getInt(ID), resultSet.getString(NAME),
                resultSet.getString(SHORTNAME), resultSet.getString(DESCRIPTION));
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param parameters Project object
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, Project project)  throws SQLException {
        preparedStatement.setString(1, (String) project.getName());
        preparedStatement.setString(2, (String) project.getShortName());
        preparedStatement.setString(3, (String) project.getDescription());
    }

}
