package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import java.util.List;

import javax.servlet.http.HttpSession;

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
     */
    List<Task> getTasksList();

    /**
     * Remove project from database by id
     *
     * @param id project's id
     */
    void removeTask(int id);

    /**
     * Create new task in database
     * @param task data from add task form
     * @param session {@link HttpSession} object
     * @return list of tasks with added new task
     */
    List<Task> addTask(Task task, HttpSession session);

    /**
     * Create new task in session
     * @param task data from add task form
     */
    void addTask(Task task);

    /**
     * Get task by id
     *
     * @param id task's id
     * @return Task object
     */
    Task getTaskById(int id);

    /**
     * Update task in database
     * @param task data from update task form
     */
    void updateTask(Task task);

    /**
     * Update task in session
     * @param task data from update task
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