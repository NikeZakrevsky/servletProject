package com.qulix.zakrevskynp.trainingtask.web.task.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.task.Task;

/**
 * CRUD operations for tasks
 * @author Q-NZA
 */
public interface TasksDAO {

    /**
     * Get all task from database
     *
     * @return list of all tasks in database
     * @throws CustomException
     */
    List<Task> getTasksList() throws CustomException;

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    void removeTask(int id) throws CustomException;

    /**
     * Insert task in database
     *
     * @param parameters {@link Task} object
     * @throws CustomException
     */
    List<Map<String, Object>> addTask(Map<String, Object> parameters, HttpSession session) throws CustomException;

    void addTask(Map<String, Object> parameters) throws CustomException;

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    Task getTaskById(int id) throws CustomException;

    /**
     *
     * @param parameters {@link Task} object
     * @throws SQLException
     */
    void updateTask(Map<String, Object> parameters)  throws CustomException;
    void updateTask(Map<String, Object> parameters, HttpSession session, int id)  throws CustomException;
    /**
     *
     * @param id project id
     * @return List of tasks with specified project id
     * @throws CustomException
     */
    List<Map<String, Object>> getTasksByProjectId(int id) throws CustomException;
}
