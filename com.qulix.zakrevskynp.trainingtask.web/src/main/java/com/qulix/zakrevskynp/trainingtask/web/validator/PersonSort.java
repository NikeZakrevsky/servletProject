package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

public class PersonSort implements Sort<Person> {

    public List<Person> sort(List<Person> persons, String sortField) {
        if (sortField == null) {
            sortField = "fname";
        }

        Map<String, Comparator> comparators = new HashMap<>();
        comparators.put("fname", Comparator.comparing(Person::getFname));
        comparators.put("sname", Comparator.comparing(Person::getSname));
        comparators.put("lname", Comparator.comparing(Person::getLname));
        comparators.put("position", Comparator.comparing(Person::getPosition));

        persons.sort(comparators.get(sortField));
        return persons;
    }
}
