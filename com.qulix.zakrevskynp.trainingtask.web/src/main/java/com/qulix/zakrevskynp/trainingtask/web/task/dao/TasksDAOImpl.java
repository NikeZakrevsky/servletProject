package com.qulix.zakrevskynp.trainingtask.web.task.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.person.Person;
import com.qulix.zakrevskynp.trainingtask.web.person.dao.PersonDAOImpl;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;

/**
 * Implementation of {@link TasksDAO} interface
 * @author Q-NZA
 */
public class TasksDAOImpl implements TasksDAO {
    
    private static final String SELECT_QUERY = "SELECT id, name, time, startDate, endDate, status, shortname, projectId, personId, fname + ' ' + sname + ' ' + lname as person FROM tasks left join projects on tasks.projectId = projects.id left join persons on tasks.personId = persons.id";
    private static final String DELETE_QUERY = "delete from tasks where id=?";
    private static final String INSERT_QUERY = "insert into tasks(name, time, startDate, endDate, status, projectId, personId) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update tasks set name = ?, time = ?, startDate = ?, endDate = ?, status = ?, projectId = ?, personId = ? where id = ?";

    private static int id = 0;

    private TaskUtil taskUtil = new TaskUtil();

    /**
     * Get all task from database
     *
     * @return list of all tasks in database
     * @throws CustomException
     */
    public List<Task> getTasksList() throws CustomException {
        List<Task> tasks = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            while (resultSet.next()) {
                Task task = taskUtil.resultSetAsObject(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new CustomException("Error while getting tasks list", e);
        }
        return tasks;
    }

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    public void removeTask(int id) throws CustomException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new CustomException("Error while removing task", e);
        }
    }

    /**
     * Insert task in database
     *
     * @param parameters task form data
     * @throws CustomException
     */
    public void addTask(Map<String, Object> parameters) throws CustomException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
        ) {
            taskUtil.setPreparedStatement(preparedStatement, parameters);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new CustomException("Error while adding task", e);
        }
    }

    @Override
    public List<Map<String, Object>> addTask(Map<String, Object> parameters, HttpSession session) throws CustomException {
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        parameters.put("id", generateId());
        if(parameters.get("personId") != null) {
            Person person = new PersonDAOImpl().getPersonById((int) parameters.get("personId"));
            parameters.put("performer", person.getFname() + " " + person.getSname() + " " + person.getLname());
        }
        tasks.add(parameters);
        return tasks;
    }

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    public Task getTaskById(int id) throws CustomException {
        Task task;
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + " where id = ?")
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            task = taskUtil.resultSetAsObject(resultSet);
            connection.commit();
        } catch (SQLException e) {
            throw new CustomException("Error while getting task", e);
        }
        return task;
    }

    /**
     *
     * @param parameters task form data
     * @throws CustomException
     */
    public void updateTask(Map<String, Object> parameters) throws CustomException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            taskUtil.setPreparedStatement(preparedStatement, parameters);
            preparedStatement.setInt(8, Integer.parseInt(parameters.get("id").toString()));
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new CustomException("Error while updating task", e);
        }
    }

    @Override
    public void updateTask(Map<String, Object> parameters, HttpSession session, int id) throws CustomException {
        List<Map<String, Object>> tasks = (List<Map<String, Object>>) session.getAttribute("tasks");
        Iterator iterator = tasks.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map<String, Object> task = (Map<String, Object>) iterator.next();
            if ((Integer) task.get("id") == id) {
                parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
                if (parameters.get("personId") != null) {
                    Person person = new PersonDAOImpl().getPersonById((int) parameters.get("personId"));
                    parameters.put("performer", person.getFname() + " " + person.getSname() + " " + person.getLname());
                }
                tasks.set(i, parameters);
                break;
            }
            i++;
        }
        session.setAttribute("tasks", tasks);
    }

    /**
     *
     * @param id project id
     * @return List of tasks with specified project id
     * @throws CustomException
     */
    public List<Task> getTasksByProjectId(int id) throws CustomException {
        List<Task> tasks = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY + " where projectId = ?")
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = taskUtil.resultSetAsObject(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new CustomException("Error while getting tasks", e);
        }
        return tasks;
    }

    private int generateId() {
        return id++;
    }

}
