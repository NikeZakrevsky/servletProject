package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Validate project form data
 * @author Q-NZA
 */
public class ProjectDataValidator {
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        Predicate<Object> isEmptyAndLength = e -> e.equals("") || e.toString().length() > 20;

        if (isEmptyAndLength.test(parameters.get("name"))) errors.add("Неверное поле название");
        if (isEmptyAndLength.test(parameters.get("shortName"))) errors.add("Неверное поле сокращённое название");
        if (isEmptyAndLength.test(parameters.get("description"))) errors.add("Неверное поле описание");

        return errors;
    }
}
