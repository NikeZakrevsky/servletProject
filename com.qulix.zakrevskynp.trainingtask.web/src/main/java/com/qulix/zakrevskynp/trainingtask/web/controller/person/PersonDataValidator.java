package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate person form data
 * @author Q-NZA
 */
public class PersonDataValidator extends Validator{

    private static final String FIRST_NAME_ERROR = "Неверное поле имя";
    private static final String MIDDLE_NAME_ERROR = "Неверное поле фамилия";
    private static final String LAST_NAME_ERROR = "Неверное поле отчество";
    private static final String POSITION_ERROR = "Неверное поле должность";

    /**
     * Validate person information
     * @param parameters person form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        parseIntegerParams("id", parameters);
        validateField(parameters.get("firstName"), FIRST_NAME_ERROR, errors);
        validateField(parameters.get("middleName"), MIDDLE_NAME_ERROR, errors);
        validateField(parameters.get("lastName"), LAST_NAME_ERROR, errors);
        validateField(parameters.get("position"), POSITION_ERROR, errors);

        return errors;
    }
}
