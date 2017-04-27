package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.List;

/**
 * Person entity model
 *
 * @author Q-NZA
 */
public class Person {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;
    private List<Task> task;

    /**
     * Create person object
     *
     * @param id id of person
     * @param firstName first name
     * @param middleName middle name
     * @param lastName last name
     * @param position position
     */
    public Person(Integer id, String firstName, String middleName, String lastName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.position = position;
    }

    /**
     * Gets id
     *
     * @return person id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets person first name
     *
     * @return person first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets person middle name
     *
     * @return person middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets person last name
     *
     * @return person last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets person position
     *
     * @return person position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Gets list of tasks
     *
     * @return list of tasks
     */
    public List<Task> getTask() {
        return task;
    }

    /**
     * Sets list of tasks
     *
     * @param task list of tasks
     */
    public void setTask(List<Task> task) {
        this.task = task;
    }
}
