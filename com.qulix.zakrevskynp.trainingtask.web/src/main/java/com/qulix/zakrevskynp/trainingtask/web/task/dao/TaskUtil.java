package com.qulix.zakrevskynp.trainingtask.web.task.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.task.Task;

/**
 * Utilites for tasks dao layer
 * @author Q-NZA
 */
public class TaskUtil {
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
     * Convert the ResultSet to a List of Maps, where each Map represents a row with columnNames and columValues
     * @param rs
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()){
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

    /**
     *
     * @param preparedStatement link of the prepared statement for setting parameters
     * @param parameters task form data
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
}
