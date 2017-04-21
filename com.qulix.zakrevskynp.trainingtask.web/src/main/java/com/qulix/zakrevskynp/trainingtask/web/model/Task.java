package com.qulix.zakrevskynp.trainingtask.web.model;

import java.sql.Date;
import java.time.Duration;

/**
 * Task entity model
 * @author Q-NZA
 */
public class Task extends BaseDAOEntity {
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
    private Person person;

    /**
     * Create task
     */
    public Task(Integer id, String name, Duration workTime, Date startDate, Date endDate, TaskStatus taskStatus,
        String performer) {
        this.id = id;
        this.name = name;
        this.workTime = workTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskStatus = taskStatus;
        this.performer = performer;
    }

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
    public Duration getWorkTime() {
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

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", workTime=" + workTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", taskStatus=" + taskStatus +
                ", performer='" + performer + '\'' +
                ", projectShortName='" + projectShortName + '\'' +
                ", projectId=" + projectId +
                ", personId=" + personId +
                ", person=" + person +
                '}';
    }
}
