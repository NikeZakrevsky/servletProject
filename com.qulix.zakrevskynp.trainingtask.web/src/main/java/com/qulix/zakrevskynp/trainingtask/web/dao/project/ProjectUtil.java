package com.qulix.zakrevskynp.trainingtask.web.dao.project;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.DaoUtil;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;

/**
 * Utilities for project'd dao layer
 * @author Q-NZA
 */
class ProjectUtil implements DaoUtil<Project> {
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
    public Project resultSetAsObject(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        String shortName = resultSet.getString(SHORTNAME);
        String description = resultSet.getString(DESCRIPTION);

        return new Project(id, name, shortName, description);
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param project Project object
     * @throws SQLException throws while setting parameters in @{{@link PreparedStatement}}
     */
    public int setPreparedStatement(PreparedStatement preparedStatement, Project project) throws SQLException {
        preparedStatement.setString(1, project.getName());
        preparedStatement.setString(2, project.getShortName());
        preparedStatement.setString(3, project.getDescription());
        return 4;
    }

    /**
     * Convert the ResultSet to a List of objects
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    public List<Project> resultSetToList(ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<>();
        while (rs.next()) {
            projects.add(resultSetAsObject(rs));
        }
        return projects;
    }

}
