package com.qulix.trainingtask.dao.project;

import com.qulix.trainingtask.dao.exception.DAOException;
import com.qulix.trainingtask.model.Project;

import java.util.List;

/**
 * CRUD operations for projects
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
     * @param name project's name
     * @param shortName project's short name
     * @param description project's description
     * @throws DAOException
     */
    void addProject(String name, String shortName, String description) throws DAOException;

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
     * @param id project's id
     * @param name project's name
     * @param shortName project's short name
     * @param description project's description
     * @throws DAOException
     */
    void updateProject(int id, String name, String shortName, String description) throws DAOException;

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     * @throws DAOException
     */
    Project getProjectById(int id) throws DAOException;
}
