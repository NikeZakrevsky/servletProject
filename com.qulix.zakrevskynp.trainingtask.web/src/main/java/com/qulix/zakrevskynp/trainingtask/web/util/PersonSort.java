package com.qulix.zakrevskynp.trainingtask.web.util;

import java.util.List;
import java.util.Comparator;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

public class PersonSort implements Sort<Person> {

    public List<Person> sort(List<Person> persons, String sortField) {
        Comparator comparator = null;
        if (sortField == null) {
            sortField = "fname";
        }
        switch(sortField) {
            case "fname":
                comparator = Comparator.comparing(Person::getFname);
                break;
            case "sname":
                comparator = Comparator.comparing(Person::getSname);
                break;
            case "lname":
                comparator = Comparator.comparing(Person::getLname);
                break;
            case "position":
                comparator = Comparator.comparing(Person::getPosition);
                break;
            default:
                comparator = Comparator.comparing(Person::getFname);
                break;
        }
        persons.sort(comparator);
        return persons;
    }
}
