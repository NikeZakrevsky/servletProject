package com.qulix.zakrevskynp.trainingtask.web.task.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

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
     */
    List<Task> getTasksList();

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    boolean removeTask(int id);

    /**
     * Insert task in database
     * @param parameters data from add task form
     * @param session {@link HttpSession} object for getting tasks list
     * @return list of tasks with added new task
     */
    List<Task> addTask(Task task, HttpSession session);

    /**
     *
     * @param parameters
     * @return
     */
    boolean addTask(Task task);

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    Task getTaskById(int id);

    /**
     * Update task in database
     * @param parameters data from update task form
     * @throws SQLException
     */
    boolean updateTask(Task task);

    /**
     * Update task in session
     * @param parameters data from update task form for getting tasks list
     * @param session {@link HttpSession} object
     * @param id task's id
     */
    void updateTask(Task task, HttpSession session, int id);

    /**
     * @param id project id
     * @return List of tasks with specified project id
     */
    List<Task> getTasksByProjectId(int id);
}
