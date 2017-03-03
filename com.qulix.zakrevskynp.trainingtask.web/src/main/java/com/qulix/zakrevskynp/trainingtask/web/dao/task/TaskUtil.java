package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Utilites for tasks dao layer
 * @author Q-NZA
 */
public class TaskUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String STARTDATE = "start_date";
    private static final String ENDDATE = "end_date";
    private static final String STATUS = "status";
    private static final String PROJECTID = "projectId";
    private static final String PERSONID = "personId";
    private static final String SHORTNAME = "shortname";
    private static final String PERSON = "person";

    /**
     * Create Task object from ResultSet
     *
     * @param resultSet resultSet for converting to object
     * @return created task object
     * @throws SQLException
     */
    public Task resultSetAsObject(ResultSet resultSet) throws SQLException {
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
     *
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param parameters {@link Task} object
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, Map<String, Object> parameters) throws SQLException {
        preparedStatement.setString(1, parameters.get(NAME).toString());
        preparedStatement.setInt(2, Integer.parseInt(parameters.get(TIME).toString()));
        preparedStatement.setDate(3, (java.sql.Date)parameters.get(STARTDATE));
        preparedStatement.setDate(4, (java.sql.Date)parameters.get(ENDDATE));
        preparedStatement.setString(5, (String)parameters.get(STATUS));
        preparedStatement.setObject(6, parameters.get(PROJECTID));
        preparedStatement.setObject(7, parameters.get(PERSONID));
    }

    /**
     * Convert string to date
     *
     * @param sourceDate string for converting
     * @return converted Date object
     */
    public Date toDate(Object sourceDate) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyy-MM-dd");
        java.util.Date date = sdf1.parse(sourceDate.toString());
        return new java.sql.Date(date.getTime());
    }
}
