package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.Date;
import java.time.Duration;

/**
 * Task entity model.
 *
 * @author Q-NZA
 */
public class Task extends BaseDaoEntity {
    private String name;
    private Duration workTime;
    private Date startDate;
    private Date endDate;
    private TaskStatus status;
    private String projectShortName;
    private Integer projectId;
    private Person person;

    /**
     * Constructing of the Task object.
     *
     * @param id id of task.
     * @param name name.
     * @param workTime working time.
     * @param startDate starting date.
     * @param endDate ending date.
     * @param status status of the task.
     * @param person person.
     */
    public Task(Integer id, String name, Duration workTime, Date startDate, Date endDate, TaskStatus status,
        Person person) {
        super.setId(id);
        this.name = name;
        this.workTime = workTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
    }

    public Task(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets name of the task.
     *
     * @return task name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets work time of the task.
     *
     * @return task work time.
     */
    public Duration getWorkTime() {
        return workTime;
    }
    
    /**
     * Gets start date of the task.
     *
     * @return task start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets end date of the task.
     *
     * @return task end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets status of the task.
     *
     * @return task status.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Gets short name of the project.
     *
     * @return project short name.
     */
    public String getProjectShortName() {
        return projectShortName;
    }

    /**
     * Gets id of the project.
     *
     * @return project id.
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Sets id of the project.
     *
     * @param projectId id of the project.
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * Sets short name of the project.
     *
     * @param projectShortName short name of the project.
     */
    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    /**
     * Gets person of the task.
     *
     * @return Person object.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets person of the task.
     *
     * @param person Person object.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", workTime=" + workTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", projectShortName='" + projectShortName + '\'' +
                ", projectId=" + projectId +
                ", person=" + person +
                '}';
    }
}
