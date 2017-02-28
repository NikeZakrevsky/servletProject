package com.qulix.zakrevskynp.trainingtask.dao.task;

import com.qulix.zakrevskynp.trainingtask.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.model.Task;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD operations for tasks
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
    void removeTask(int id);

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
    void addTask(String name, int time, String startDate, String endDate, String status, String projectId, String personId) throws DAOException;

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    Task getTaskById(int id);

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
    void updateTask(int id, String name, int time, String startDate, String endDate, String status, String projectId, String personId) throws SQLException;
}
