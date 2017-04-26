package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate data from the forms of working with the persons
 *
 * @author Q-NZA
 */
public class PersonDataValidator extends Validator {

    private static final String FIRST_NAME = "Имя";
    private static final String MIDDLE_NAME = "Фамилия";
    private static final String LAST_NAME = "Отчество";
    private static final String POSITION = "Должность";
    private static final String ID = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String MIDDLE_NAME_FIELD = "middleName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String POSITION_FIELD = "position";

    /**
     * Validates person data
     *
     * @param parameters person form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();
        parseIntegerParams(ID, parameters);
        validateFieldEmpty(parameters.get(FIRST_NAME_FIELD), FIRST_NAME, errors);
        validateFieldLength(parameters.get(FIRST_NAME_FIELD), FIRST_NAME, errors, 20);
        validateFieldSymbols(parameters.get(FIRST_NAME_FIELD), FIRST_NAME, errors);
        validateFieldEmpty(parameters.get(MIDDLE_NAME_FIELD), MIDDLE_NAME, errors);
        validateFieldLength(parameters.get(MIDDLE_NAME_FIELD), MIDDLE_NAME, errors, 20);
        validateFieldSymbols(parameters.get(MIDDLE_NAME_FIELD), MIDDLE_NAME, errors);
        validateFieldEmpty(parameters.get(LAST_NAME_FIELD), LAST_NAME, errors);
        validateFieldLength(parameters.get(LAST_NAME_FIELD), LAST_NAME, errors, 20);
        validateFieldSymbols(parameters.get(LAST_NAME_FIELD), LAST_NAME, errors);
        validateFieldEmpty(parameters.get(POSITION_FIELD), POSITION, errors);
        validateFieldLength(parameters.get(POSITION_FIELD), POSITION, errors, 20);
        return errors;
    }
}
