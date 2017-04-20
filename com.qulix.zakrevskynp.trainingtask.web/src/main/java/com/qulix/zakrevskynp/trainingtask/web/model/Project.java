package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Project entity model
 * @author Q-NZA
 */
public class Project extends BaseDAOEntity {
    private Integer id;
    private String name;
    private String shortName;
    private String description;
    private List<Task> tasksList = new ArrayList<>();

    /**
     * Create project object
     * @param id project id
     * @param name project name
     * @param shortName project short name
     * @param description project description
     */
    public Project(Integer id, String name, String shortName, String description) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
    }

    /**
     * Get project id
     * @return project id
     */
    public int getId() {
        return id;
    }

    /**
     * Get project id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get project name
     * @return project name
     */
    public String getName() {
        return name;
    }

    /**
     * Get project name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get project short name
     * @return project short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Get project description
     * @return project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get project tasks
     * @return project tasks
     */
    public List<Task> getTasks() {
        return tasksList;
    }

    /**
     * Get project tasks
     */
    public void setTasks(List<Task> tasksList) {
        this.tasksList = tasksList;
    }
}
