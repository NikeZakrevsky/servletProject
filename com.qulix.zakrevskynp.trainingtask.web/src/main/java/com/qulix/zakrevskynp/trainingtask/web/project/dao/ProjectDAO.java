package com.qulix.zakrevskynp.trainingtask.web.project.dao;

import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.CustomException;
import com.qulix.zakrevskynp.trainingtask.web.project.Project;

/**
 * CRUD operations for projects
 * @author Q-NZA
 */
public interface ProjectDAO {

    /**
     * Get all projects from database
     *
     * @return list of all projects from database
     * @throws CustomException
     */
    List<Project> getProjectsList() throws CustomException;

    /**
     * Insert project to database
     *
     * @param parameters {@link Project} object
     * @throws CustomException
     */
    void addProject(Map<String, Object> parameters, List<Map<String, Object>> tasks) throws CustomException;

    /**
     * Remove project from database by id
     *
     * @param id
     * @throws CustomException
     */
    void removeProject(int id) throws CustomException;

    /**
     * Update information about project in database
     *
     * @param parameters {@link Project} object
     * @throws CustomException
     */
    void updateProject(Map<String, Object> parameters) throws CustomException;

    /**
     * Get project id
     *
     * @param id project's id
     * @return Project object
     * @throws CustomException
     */
    Project getProjectById(int id) throws CustomException;
}
