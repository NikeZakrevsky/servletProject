package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validate person form data
 * @author Q-NZA
 */
public class PersonDataValidator {

    /**
     * Validate person information
     * @param parameters Person object for validation
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        if (parameters.get("fname").toString().equals("")) {
            errors.add("Имя не должно быть пустым");
        }
        if (parameters.get("fname").toString().length() > 20) {
            errors.add("Имя слишком длинное");
        }

        if (parameters.get("sname").toString().equals("")) {
            errors.add("Фамилия не должна быть пустой");
        }
        if (parameters.get("sname").toString().length() > 20) {
            errors.add("Фамилия слишком длинная");
        }

        if (parameters.get("lname").toString().equals("")) {
            errors.add("Отчество не должно быть пустым");
        }
        if (parameters.get("lname").toString().length() > 20) {
            errors.add("Отчество слишком длинное"); }

        if (parameters.get("position").equals("")) {
            errors.add("Должность не должна быть пустой");
        }
        if (parameters.get("position").toString().length() > 20) {
            errors.add("Должность слишком длинная");
        }

        return errors;
    }
}
