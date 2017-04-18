package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * CRUD operations for projects
 * @author Q-NZA
 */
public interface ProjectDAO {

    /**
     * Get all projects from database
     * @return list of all projects from database
     */
    List getProjectsList();

    /**
     * Insert project to database
     * @param project {@link Project} object
     */
    void addProject(Project project, List<Task> tasks);

    /**
     * Remove project from database by id
     * @param id project id
     */
    void removeProject(int id);

    /**
     * Update information about project in database
     * @param project {@link Project} object
     */
    void updateProject(Project project);

    /**
     * Get project id
     * @param id project's id
     * @return Project object
     */
    Project getProjectById(int id);
}
