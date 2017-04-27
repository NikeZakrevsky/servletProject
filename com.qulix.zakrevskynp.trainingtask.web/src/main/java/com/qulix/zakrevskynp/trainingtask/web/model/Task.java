package com.qulix.zakrevskynp.trainingtask.web.model;

import java.sql.Date;
import java.time.Duration;

/**
 * Task entity model
 *
 * @author Q-NZA
 */
public class Task {
    private Integer id;
    private String name;
    private Duration workTime;
    private Date startDate;
    private Date endDate;
    private TaskStatus status;
    private String performer;
    private String projectShortName;
    private Integer projectId;
    private Integer personId;

    /**
     * Constructing of the Task object
     *
     * @param id id of task
     * @param name name
     * @param workTime working time
     * @param startDate starting date
     * @param endDate ending date
     * @param status status of the task
     * @param performer performer
     */
    public Task(Integer id, String name, Duration workTime, Date startDate, Date endDate, TaskStatus status,
        String performer) {
        this.id = id;
        this.name = name;
        this.workTime = workTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.performer = performer;
    }

    /**
     * Constructing of the Task object
     */
    public Task() {
    }

    /**
     * Gets id of task
     *
     * @return task id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id of task
     *
     * @param id id of task
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name of task
     *
     * @return task name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets work time of task
     *
     * @return task  work time
     */
    public Duration getWorkTime() {
        return workTime;
    }
    
    /**
     * Gets start date of task
     *
     * @return task start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets end date of task
     *
     * @return task end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets status of task
     *
     * @return task status
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Sets performer of task
     *
     * @param performer
     */
    public void setPerformer(String performer) {
        this.performer = performer;
    }

    /**
     * Gets performer of task
     *
     * @return task performer
     */
    public String getPerformer() {
        return performer;
    }

    /**
     * Gets short name of project
     *
     * @return project short name
     */
    public String getProjectShortName() {
        return projectShortName;
    }

    /**
     * Gets id of project
     *
     * @return project id
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets id of project
     *
     * @param projectId id of project
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets id of person
     *
     * @return person id
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * Sets short name of the project
     *
     * @param projectShortName short name of the project
     */
    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    /**
     * Sets id of the person
     *
     * @param personId id of the person
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
