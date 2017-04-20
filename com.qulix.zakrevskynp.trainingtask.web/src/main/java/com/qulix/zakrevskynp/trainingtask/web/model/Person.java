package com.qulix.zakrevskynp.trainingtask.web.model;

import java.util.List;

/**
 * Person entity model
 * @author Q-NZA
 */
public class Person extends BaseDAOEntity {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;
    private List<Task> task;

    /**
     * Create person object
     * @param id person id
     * @param firstName person first name
     * @param middleName person middle name
     * @param lastName person last name
     * @param position person position
     */
    public Person(Integer id, String firstName, String middleName, String lastName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.position = position;
    }

    /**
     * Get person id
     * @return person id
     */
    public int getId() {
        return id;
    }

    /**
     * Get person id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get person first name
     * @return person first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get person middle name
     * @return person middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Get person last name
     * @return person last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get person position
     * @return person position
     */
    public String getPosition() {
        return position;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }
}
