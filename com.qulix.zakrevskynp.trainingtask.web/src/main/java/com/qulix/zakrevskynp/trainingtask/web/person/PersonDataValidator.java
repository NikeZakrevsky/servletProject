package com.qulix.zakrevskynp.trainingtask.web.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Validate person form data
 * @author Q-NZA
 */
public class PersonDataValidator {

    /**
     * Validate person information
     * @param parameters person form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        Predicate<Object> isEmptyAndLength = e -> e.equals("") || e.toString().length() > 20;

        if (!parameters.get("id").equals("")) {
            parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
        }
        else {
            parameters.put("id", null);
        }

        if (isEmptyAndLength.test(parameters.get("fname"))) {
            errors.add("Неверное поле имя");
        }
        if (isEmptyAndLength.test(parameters.get("sname"))) {
            errors.add("Неверное поле фамилия");
        }
        if (isEmptyAndLength.test(parameters.get("lname"))) {
            errors.add("Неверное поле отчество");
        }
        if (isEmptyAndLength.test(parameters.get("position"))) {
            errors.add("Неверное поле должность");
        }
        return errors;
    }
}
