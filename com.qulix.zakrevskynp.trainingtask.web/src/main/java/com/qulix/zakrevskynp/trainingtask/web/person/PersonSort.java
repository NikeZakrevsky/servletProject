package com.qulix.zakrevskynp.trainingtask.web.person;

import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

public class PersonSort {

    public List<Person> sort(List<Person> persons, String sortField) {
        if (sortField == null) {
            sortField = "fname";
        }

        Map<String, Comparator> comparators = new HashMap<>();
        comparators.put("fname", Comparator.comparing(Person::getFirstName));
        comparators.put("sname", Comparator.comparing(Person::getMiddleName));
        comparators.put("lname", Comparator.comparing(Person::getLastName));
        comparators.put("position", Comparator.comparing(Person::getPosition));

        persons.sort(comparators.get(sortField));
        return persons;
    }
}
