package com.qulix.zakrevskynp.trainingtask.web.project.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.qulix.zakrevskynp.trainingtask.web.project.Project;

/**
 * Utilities for project'd dao layer
 * @author Q-NZA
 */
class ProjectUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORTNAME = "shortName";
    private static final String DESCRIPTION = "description";

    /**
     * Create object from ResutSet
     *
     * @param resultSet resultSet for converting to object
     * @return created project object
     * @throws SQLException throws while getting data from @{{@link ResultSet}}
     */
    Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        return new Project(resultSet.getInt(ID), resultSet.getString(NAME),
                resultSet.getString(SHORTNAME), resultSet.getString(DESCRIPTION));
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param project Project object
     * @throws SQLException throws while setting parameters in @{{@link PreparedStatement}}
     */
    void setPreparedStatement(PreparedStatement preparedStatement, Project project) throws SQLException {
        preparedStatement.setString(1, project.getName());
        preparedStatement.setString(2, project.getShortName());
        preparedStatement.setString(3, project.getDescription());
    }

}
