package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import com.qulix.zakrevskynp.trainingtask.web.dao.ConnectionFactory;
import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of {@link TasksDAO} interface
 */
public class TasksDAOImpl implements TasksDAO {
    
    private static final String SELECT_QUERY = "SELECT id, name, time, start_date, end_date, status, shortname, project_id, person_id, fname + ' ' + sname + ' ' + lname as person FROM tasks left join projects on tasks.project_id = projects.id left join persons on tasks.person_id = persons.id";
    private static final String DELETE_QUERY = "delete from tasks where id=?";
    private static final String INSERT_QUERY = "insert into tasks(name, time, start_date, end_date, status, project_id, person_id) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update tasks set name = ?, time = ?, start_date = ?, end_date = ?, status = ?, project_id = ?, person_id = ? where id = ?";
    
    private TaskUtil taskUtil = new TaskUtil();
    private Logger logger = Logger.getLogger(TasksDAOImpl.class.getName());

    /**
     * Get all task from database
     *
     * @return list of all tasks in database
     * @throws DAOException
     */
    public List<Task> getTasksList() throws DAOException {
        List<Task> tasks = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            System.out.println(resultSet.getFetchSize());
            while (resultSet.next()) {
                Task task = taskUtil.resultSetAsObject(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
        return tasks;
    }

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    public void removeTask(int id) {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Insert task in database
     *
     * @param name task's name
     * @param time task's time for job
     * @param startDate task's start date
     * @param endDate task's end date
     * @param status task's status
     * @param projectId id of project
     * @param personId id of person
     * @throws DAOException
     */
    public void addTask(String name, int time, String startDate, String endDate, String status, String projectId, String personId) throws DAOException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
        ) {
            taskUtil.setPreparedStatement(preparedStatement, name, time, taskUtil.toDate(startDate), taskUtil.toDate(endDate), status, projectId, personId);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    public Task getTaskById(int id) throws DAOException {
        Task task = null;
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
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
        return task;
    }

    /**
     *
     * @param id task's id
     * @param name task's name
     * @param time task's time for job
     * @param startDate task's start date
     * @param endDate task's end date
     * @param status task's status
     * @param projectId id of project
     * @param personId id of person
     * @throws SQLException
     */
    public void updateTask(int id, String name, int time, String startDate, String endDate, String status, String projectId, String personId) throws DAOException {
        try (
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            taskUtil.setPreparedStatement(preparedStatement, name, time, taskUtil.toDate(startDate),  taskUtil.toDate(endDate), status, projectId, personId);
            preparedStatement.setInt(8, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException(e);
        }
    }

}
