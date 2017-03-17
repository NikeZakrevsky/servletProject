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
     * @
     */
    List<Task> getTasksList() ;

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    boolean removeTask(int id) ;

    /**
     * Insert task in database
     *
     * @param parameters {@link Task} object
     * @
     */
    List<Map<String, Object>> addTask(Map<String, Object> parameters, HttpSession session) ;

    boolean addTask(Map<String, Object> parameters) ;

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    Task getTaskById(int id) ;

    /**
     *
     * @param parameters {@link Task} object
     * @throws SQLException
     */
    boolean updateTask(Map<String, Object> parameters)  ;
    void updateTask(Map<String, Object> parameters, HttpSession session, int id)  ;
    /**
     *
     * @param id project id
     * @return List of tasks with specified project id
     * @
     */
    List<Map<String, Object>> getTasksByProjectId(int id) ;
}
