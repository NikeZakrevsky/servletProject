package com.qulix.zakrevskynp.trainingtask.web.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Validating data from forms
 * @author Q-NZA
 */
public abstract class Validator {

    private static final String EMPTY_ERROR = "пустое поле";
    private static final String LENGTH_ERROR = "длина поля превышает 20 символов";
    private static final String SYMBOLS_ERROR = "поле может содержать только латинские и русские буквы";
    private static final String NUMBER_ERROR = "неверный формат поля";
    private static final String REGEX = "^[a-zA-ZА-Яа-яёЁ\\s]*$";
    private static final String REGEX1 = "\\d{1,8}(.\\d)?";
    private Predicate<Object> testEmpty = e -> e == null || e.equals("");

    /**
     * Validating field length
     * @param field
     * @param fieldName
     * @param errors
     * @param fieldLength
     */
    protected void validateFieldLength(Object field, String fieldName, List<String> errors, int fieldLength) {
        if (field.toString().length() > fieldLength) {
            errors.add(fieldName + " : " + LENGTH_ERROR);
        }
    }

    /**
     * Validating field empties
     * @param field
     * @param fieldName
     * @param errors
     */
    protected void validateFieldEmpty(Object field, String fieldName, List<String> errors) {
        if (testEmpty.test(field)) {
            errors.add(fieldName + " : " + EMPTY_ERROR);
        }
    }

    /**
     * Parsing string field to integer
     * @param field
     * @param parameters
     */
    protected void parseIntegerParams(String field, Map<String, Object> parameters) {
        if (!parameters.get(field).toString().equals("")) {
            parameters.put(field, Integer.parseInt(parameters.get(field).toString()));
        }
        else {
            parameters.put(field, null);
        }
    }

    /**
     * Parsing string field to float
     * @param field
     * @param parameters
     */
    protected void parseFloatParams(String field, Map<String, Object> parameters) {
        if (!parameters.get(field).toString().equals("")) {
            parameters.put(field, Float.parseFloat(parameters.get(field).toString()));
        }
        else {
            parameters.put(field, null);
        }
    }

    /**
     * Checking field symbols
     * @param field
     * @param fieldName
     * @param errors
     */
    protected void validateFieldSymbols(Object field, String fieldName, List<String> errors) {
        if (!field.toString().matches(REGEX)) {
            errors.add(fieldName + " : " + SYMBOLS_ERROR);
        }
    }

    protected void validateFieldNumbers(Object field, String fieldName, List<String> errors) {
        if (!field.toString().matches(REGEX1)) {
            errors.add(fieldName + " : " + NUMBER_ERROR);
        }
    }
}
