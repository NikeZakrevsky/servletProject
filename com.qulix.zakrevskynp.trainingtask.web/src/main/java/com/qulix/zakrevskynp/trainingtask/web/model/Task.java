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
    private Status status;
    private String performer;
    private String projectShortName;
    private Integer projectId;
    private Integer personId;

    /**
     * Create task project
     */
    public Task() {
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
     * Get task work time
     */
    public void setTime(Duration time) {
        this.workTime = time;
    }

    /**
     * Get task start date
     * @return task start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get task start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get task end date
     * @return task end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Get task end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get task status
     * @return task status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Get task status
     */
    public void setStatus(String status) {
        this.status = Status.valueOf(status);
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
     * Get project short name
     */
    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
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

    /**
     * Get task person id
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }


}
