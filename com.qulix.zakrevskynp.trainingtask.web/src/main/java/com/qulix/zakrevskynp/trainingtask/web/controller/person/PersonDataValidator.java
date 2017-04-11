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

    private static final String FIRST_NAME_ERROR = "Неверное поле имя";
    private static final String MIDDLE_NAME_ERROR = "Неверное поле имя";
    private static final String LAST_NAME_ERROR = "Неверное поле имя";
    private static final String POSITION_ERROR = "Неверное поле имя";

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
        validateField(parameters.get("firstName"), FIRST_NAME_ERROR);
        validateField(parameters.get("middleName"), MIDDLE_NAME_ERROR);
        validateField(parameters.get("lastName"), LAST_NAME_ERROR);
        validateField(parameters.get("position"), POSITION_ERROR);

        return errors;
    }

    private void validateField(Object field, String errorMessage) {
        if (isEmptyAndLength.test(field)) {
            errors.add(errorMessage);
        }
    }
}
