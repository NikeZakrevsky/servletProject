package com.qulix.zakrevskynp.trainingtask.web.controller.person;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
     * Validation of person data
     *
     * @param parameters person data from the form
     * @return list of errors
     */
    public List<String> validate(HttpServletRequest parameters) {
        List<String> errors = new ArrayList<>();

        validateFirstName(parameters.getParameter(FIRST_NAME_FIELD), errors);
        validateMiddleName(parameters.getParameter(MIDDLE_NAME_FIELD), errors);
        validateLastName(parameters.getParameter(LAST_NAME_FIELD), errors);
        validatePosition(parameters.getParameter(POSITION_FIELD), errors);

        return errors;
    }

    private void validatePosition(String parameter, List<String> errors) {
        validateFieldEmpty(parameter, POSITION_FIELD, errors);
        validateFieldLength(parameter, POSITION_FIELD, errors, 60);
    }

    private void validateLastName(String parameter, List<String> errors) {
        validateFieldEmpty(parameter, LAST_NAME_FIELD, errors);
        validateFieldLength(parameter, LAST_NAME_FIELD, errors, 20);
        validateFieldSymbols(parameter, LAST_NAME_FIELD, errors);
    }

    private void validateMiddleName(String parameter, List<String> errors) {
        validateFieldEmpty(parameter, MIDDLE_NAME_FIELD, errors);
        validateFieldLength(parameter, MIDDLE_NAME_FIELD, errors, 20);
        validateFieldSymbols(parameter, MIDDLE_NAME_FIELD, errors);
    }

    private void validateFirstName(String parameter, List<String> errors) {
        validateFieldEmpty(parameter, FIRST_NAME_FIELD, errors);
        validateFieldLength(parameter, FIRST_NAME_FIELD, errors, 20);
        validateFieldSymbols(parameter, FIRST_NAME_FIELD, errors);
    }
}
