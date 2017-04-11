package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Validate person form data
 * @author Q-NZA
 */
public class PersonDataValidator {

    private List<String> errors = new ArrayList<>();
    private Predicate<Object> isEmptyAndLength = e -> e == null || e.equals("") || e.toString().length() > 20;
    /**
     * Validate person information
     * @param parameters person form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {

        if (!parameters.get("id").equals("")) {
            parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
        }
        else {
            parameters.put("id", null);
        }
        validateField(parameters.get("firstName"), "Неверное поле имя");
        validateField(parameters.get("middleName"), "Неверное поле фамилия");
        validateField(parameters.get("lastName"), "Неверное поле отчество");
        validateField(parameters.get("position"), "Неверное поле должность");

        return errors;
    }

    private void validateField(Object field, String errorMessage) {
        if (isEmptyAndLength.test(field)) {
            errors.add(errorMessage);
        }
    }
}
