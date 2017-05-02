package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.Date;
import java.time.Duration;

/**
 * Task entity model
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
    private Integer personId;
    private Person person;

    /**
     * Constructing of the Task object
     *
     * @param id id of task
     * @param name name
     * @param workTime working time
     * @param startDate starting date
     * @param endDate ending date
     * @param status status of the task
     * @param person person
     */
    public Task(Integer id, String name, Duration workTime, Date startDate, Date endDate, TaskStatus status,
        Person person) {
        super.id = id;
        this.name = name;
        this.workTime = workTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
    }

    /**
     * Constructing of the Task object
     */
    public Task() {
    }

    public Task(Integer projectId) {
        this.projectId = projectId;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + super.id +
                ", name='" + name + '\'' +
                ", workTime=" + workTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", person='" + person + '\'' +
                ", projectShortName='" + projectShortName + '\'' +
                ", projectId=" + projectId +
                ", personId=" + personId +
                '}';
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
