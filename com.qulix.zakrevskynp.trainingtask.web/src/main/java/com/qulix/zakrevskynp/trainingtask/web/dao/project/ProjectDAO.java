package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.dao.exception.DAOException;
import com.qulix.zakrevskynp.trainingtask.web.model.Project;

/**
 * CRUD operations for projects
 * @author Q-NZA
 */
public interface ProjectDAO {

    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     * @throws DAOException
     */
    List<Project> getProjectsList() throws DAOException;

    /**
     * Insert project to database
     *
     * @param project {@link Project} object
     * @throws DAOException
     */
    void addProject(Project project) throws DAOException;

    /**
     * Remove project from database by id
     *
     * @param id
     * @throws DAOException
     */
    void removeProject(int id) throws DAOException;

    /**
     * Update information about project in database
     *
     * @param project {@link Project} object
     * @throws DAOException
     */
    void updateProject(Project project) throws DAOException;

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     * @throws DAOException
     */
    Project getProjectById(int id) throws DAOException;
}
