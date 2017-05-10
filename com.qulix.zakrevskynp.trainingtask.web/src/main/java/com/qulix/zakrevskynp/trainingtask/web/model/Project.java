package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Project entity model
 *
 * @author Q-NZA
 */
public class Project extends BaseDaoEntity {
    private String name;
    private String shortName;
    private String description;
    private List<Task> tasksList = new ArrayList<>();

    /**
     * Creates project object
     *
     * @param id id of project
     * @param name name
     * @param shortName short name
     * @param description description
     */
    public Project(Integer id, String name, String shortName, String description) {
        super.setId(id);
        this.name = name;
        this.shortName = shortName;
        this.description = description;
    }

    /**
     * Gets name of the project
     *
     * @return project name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets short name of the project
     *
     * @return project short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Gets description of the project
     *
     * @return project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets tasks of the project
     *
     * @return project tasks
     */
    public List<Task> getTasks() {
        return tasksList;
    }

    /**
     * Sets tasks of the project
     */
    public void setTasks(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    /**
     * Sets name of the project
     *
     * @param name name of the project
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets short name of the project
     *
     * @param shortName short name of the project
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets description of the project
     *
     * @param description description of the project
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", tasksList=" + tasksList +
                '}';
    }
}
