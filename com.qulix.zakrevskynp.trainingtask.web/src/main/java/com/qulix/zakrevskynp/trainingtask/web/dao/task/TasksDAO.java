package com.qulix.zakrevskynp.trainingtask.web.dao.task;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Task;


/**
 * Interface describing the operation on @{{@link Task}} of the DAO pattern
 * @author Q-NZA
 */
public interface TasksDAO {

    List<Task> getTasksList();

    void removeTask(int id);

    List<Task> removeTask(int id, List<Task> tasks);

    List<Task> addTask(Task task, List<Task> tasks);

    void addTask(Task task);

    Task getTaskById(int id);

    void updateTask(Task task);

    List<Task> updateTask(Task task, List<Task> tasks, int id);

    List<Task> getTasksByProjectId(int id);
}
