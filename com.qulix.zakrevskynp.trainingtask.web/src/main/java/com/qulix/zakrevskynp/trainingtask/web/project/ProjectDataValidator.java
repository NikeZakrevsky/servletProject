package com.qulix.zakrevskynp.trainingtask.web.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Validate project form data
 * @author Q-NZA
 */
public class ProjectDataValidator {

    /**
     * Validate project information
     * @param parameters project form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        Predicate<Object> isEmptyAndLength = e -> e.equals("") || e.toString().length() > 20;
        System.out.println(parameters);
        if (!parameters.get("id").equals("")) {
            parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
        }
        else {
            parameters.put("id", null);
        }
        if (isEmptyAndLength.test(parameters.get("name"))) {
            errors.add("Неверное поле название");
        }

        if (isEmptyAndLength.test(parameters.get("shortName"))) {
            errors.add("Неверное поле сокращённое название");
        }

        return errors;
    }
}
