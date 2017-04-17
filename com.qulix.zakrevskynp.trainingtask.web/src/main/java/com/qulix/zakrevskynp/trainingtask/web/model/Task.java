package com.qulix.zakrevskynp.trainingtask.web.model;

import java.sql.Date;
import java.time.Duration;

/**
 * Task entity model
 * @author Q-NZA
 */
public class Task {
    private Integer id;
    private String name;
    private Duration workTime;
    private Date startDate;
    private Date endDate;
    private TaskStatus taskStatus;
    private String performer;
    private String projectShortName;
    private Integer projectId;
    private Integer personId;

    /**
     * Create task
     */
    public Task(Integer id, String name, Duration workTime, Date startDate, Date endDate, TaskStatus status, String performer, String projectShortName, Integer projectId, Integer personId) {
        this.id = id;
        this.name = name;
        this.workTime = workTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskStatus = status;
        this.performer = performer;
        this.projectShortName = projectShortName;
        this.projectId = projectId;
        this.personId = personId;
    }

    /**
     * Get task id
     * @return task id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get task id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get task name
     * @return task name
     */
    public String getName() {
        return name;
    }

    /**
     * Get task name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get task work time
     * @return task  work time
     */
    public Duration getTime() {
        return workTime;
    }
    
    /**
     * Get task start date
     * @return task start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get task end date
     * @return task end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Get task status
     * @return task status
     */
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    /**
     * Get task performer
     */
    public void setPerformer(String performer) {
        this.performer = performer;
    }

    /**
     * Get task performer
     * @return task performer
     */
    public String getPerformer() {
            return performer;
        }

    /**
     * Get project short name
     * @return project short name
     */
    public String getProjectShortName() {
        return projectShortName;
    }

    /**
     * Get project id
     * @return project id
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Get project id
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * Get person id
     * @return person id
     */
    public Integer getPersonId() {
        return personId;
    }

}
