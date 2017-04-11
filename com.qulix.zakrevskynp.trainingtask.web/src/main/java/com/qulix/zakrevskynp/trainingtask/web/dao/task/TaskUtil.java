package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Utilites for tasks dao layer
 * @author Q-NZA
 */
class TaskUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String STARTDATE = "startDate";
    private static final String ENDDATE = "endDate";
    private static final String STATUS = "status";
    private static final String PROJECTID = "projectId";
    private static final String PERSONID = "personId";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";

    /**
     * Create Task object from ResultSet
     * @param resultSet resultSet for converting to object
     * @return created task object
     */
    Task resultSetAsObject(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt(ID));
        task.setName(resultSet.getString(NAME));
        task.setTime(resultSet.getInt(TIME));
        task.setStartDate(resultSet.getDate(STARTDATE));
        task.setEndDate(resultSet.getDate(ENDDATE));
        task.setStatus(resultSet.getString(STATUS));
        task.setProjectId(resultSet.getInt(PROJECTID));
        task.setPersonId(resultSet.getInt(PERSONID));
        task.setProjectShortName(resultSet.getString(SHORTNAME));
        task.setPerformer(resultSet.getString(PERSON));
        return task;
    }

    /**
     * Convert the ResultSet to a List of Maps, where each Map represents a row with columnNames and columValues
     * @param rs @{{@link ResultSet}} object converted to list
     * @return tasks list
     * @throws SQLException throws while getting data from result set
     */
    List<Task> resultSetToList(ResultSet rs) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            tasks.add(resultSetAsObject(rs));
        }
        return tasks;
    }

    /**
     * Set parameters to prepared statement
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param task task form data
     * @throws SQLException throws while setting parameters to prepared statement
     */
    void setPreparedStatement(PreparedStatement preparedStatement, Task task) throws SQLException {
        preparedStatement.setString(1, task.getName());
        preparedStatement.setInt(2, task.getTime());
        preparedStatement.setDate(3, task.getStartDate());
        preparedStatement.setDate(4, task.getEndDate());
        preparedStatement.setString(5, task.getStatus().toString());
        preparedStatement.setObject(6, task.getProjectId());
        preparedStatement.setObject(7, task.getPersonId());
    }
}
