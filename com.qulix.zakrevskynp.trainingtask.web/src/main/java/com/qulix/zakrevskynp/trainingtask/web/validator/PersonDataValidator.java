package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Person;

/**
 * Validate person form data
 * @author Q-NZA
 */
public class PersonDataValidator {

    /**
     * Validate person information
     * @param person Person object for validation
     * @return list of errors
     */
    public List<String> validate(Person person) {
        List<String> errors = new ArrayList<>();

        if (person.getFname().equals("")) {
            errors.add("Имя не должно быть пустым");
        }
        if (person.getFname().length() > 20) {
            errors.add("Имя слишком длинное");
        }

        if (person.getSname().equals("")) {
            errors.add("Фамилия не должна быть пустой");
        }
        if (person.getSname().length() > 20) {
            errors.add("Фамилия слишком длинная");
        }

        if (person.getLname().equals("")) {
            errors.add("Отчество не должно быть пустым");
        }
        if (person.getLname().length() > 20) {
            errors.add("Отчество слишком длинное"); }

        if (person.getPosition().equals("")) {
            errors.add("Должность не должна быть пустой");
        }
        if (person.getPosition().length() > 20) {
            errors.add("Должность слишком длинная");
        }

        return errors;
    }
}
