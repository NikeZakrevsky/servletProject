package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Project entity model
 *
 * @author Q-NZA
 */
public class Project extends BaseDAOEntity {
    private String name;
    private String shortName;
    private String description;
    private List<Task> tasksList = new ArrayList<>();

    /**
     * Create project object
     *
     * @param id id of project
     * @param name name
     * @param shortName short name
     * @param description description
     */
    public Project(Integer id, String name, String shortName, String description) {
        super.id = id;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
    }

    /**
     * Gets project id
     *
     * @return project id
     */
    public int getId() {
        return super.id;
    }

    /**
     * Gets project name
     *
     * @return project name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets project short name
     *
     * @return project short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Gets project description
     *
     * @return project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets project tasks
     *
     * @return project tasks
     */
    public List<Task> getTasks() {
        return tasksList;
    }

    /**
     * Gets project tasks
     */
    public void setTasks(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + super.id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", tasksList=" + tasksList +
                '}';
    }
}
