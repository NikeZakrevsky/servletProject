package com.qulix.zakrevskynp.trainingtask.web.model;

/**
 * Person entity model
 * @author Q-NZA
 */
public class Person {
    private Integer id;
    private String fname;
    private String sname;
    private String lname;
    private String position;

    public Person() {
    }

    public Person(Integer id, String fname, String sname, String lname, String position) {
        this.id = id;
        this.fname = fname;
        this.sname = sname;
        this.lname = lname;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
