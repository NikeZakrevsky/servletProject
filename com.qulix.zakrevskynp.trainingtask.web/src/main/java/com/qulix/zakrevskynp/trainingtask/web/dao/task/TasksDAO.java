package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import java.sql.SQLException;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * CRUD operations for tasks
 * @author Q-NZA
 */
public interface TasksDAO {

    /**
     * Get all task from database
     *
     * @return list of all tasks in database
     * @throws DAOException
     */
    List<Task> getTasksList() throws DAOException;

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    void removeTask(int id) throws DAOException;

    /**
     * Insert task in database
     *
     * @param task {@link Task} object
     * @throws DAOException
     */
    void addTask(Task task) throws DAOException;

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    Task getTaskById(int id) throws DAOException;

    /**
     *
     * @param task {@link Task} object
     * @throws SQLException
     */
    void updateTask(Task task)  throws DAOException;

    List<Task> getTasksByProjectId(int id) throws DAOException;
}
