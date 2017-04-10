package com.qulix.zakrevskynp.trainingtask.web.person;

/**
 * Person entity model
 * @author Q-NZA
 */
public class Person {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;

    public Person() {
    }

    public Person(Integer id, String firstName, String middleName, String lastName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
