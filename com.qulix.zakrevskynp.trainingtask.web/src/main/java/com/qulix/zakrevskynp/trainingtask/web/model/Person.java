package com.qulix.zakrevskynp.trainingtask.web.model;

/**
 * Person entity model
 *
 * @author Q-NZA
 */
public class Person extends BaseDAOEntity {
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;

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
        super.id = id;
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
        return super.id;
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
}
