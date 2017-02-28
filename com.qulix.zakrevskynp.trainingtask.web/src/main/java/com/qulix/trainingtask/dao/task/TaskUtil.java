package com.qulix.trainingtask.dao.task;

import com.qulix.trainingtask.model.Task;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Utilites for tasks dao layer
 */
public class TaskUtil {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String STARTDATE = "start_date";
    private static final String ENDDATE = "end_date";
    private static final String STATUS = "status";
    private static final String PROJECTID = "project_id";
    private static final String PERSONID = "person_id";
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
        System.out.println(resultSet.getInt(ID));
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
     * @param name task's name
     * @param time task's time for job
     * @param startDate task's start date
     * @param endDate task's end date
     * @param status task's status
     * @param projectId id of project
     * @param personId id of person
     * @throws SQLException
     */
    public void setPreparedStatement(PreparedStatement preparedStatement, String name, int time, Date startDate, Date endDate, String status, String projectId, String personId) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, time);
        preparedStatement.setDate(3, startDate);
        preparedStatement.setDate(4, endDate);
        preparedStatement.setString(5, status);
        preparedStatement.setObject(6, projectId);
        preparedStatement.setObject(7, personId);
    }

    /**
     * Convert string to date
     *
     * @param sourceDate string for converting
     * @return converted Date object
     */
    public Date toDate(String sourceDate) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyy-MM-dd");
        java.util.Date date = null;
        try {
            date = sdf1.parse(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(date.getTime());
    }
}
