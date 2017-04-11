package com.qulix.zakrevskynp.trainingtask.web.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Validate class
 */
public abstract class Validator {

    private Predicate<Object> isEmptyAndLength = e -> e == null || e.equals("") || e.toString().length() > 20;

    /**
     * Validate single field
     * @param field Object for validate
     * @param errorMessage error message, when field isn't valid
     */
    protected void validateField(Object field, String errorMessage, List<String> errors) {
        if (isEmptyAndLength.test(field)) {
            errors.add(errorMessage);
        }
    }

    protected void parseIntegerParams(String field, Map<String, Object> parameters) {
        if (!parameters.get(field).toString().equals("")) {
            parameters.put(field, Integer.parseInt(parameters.get(field).toString()));
        }
        else {
            parameters.put(field, null);
        }
    }
}
