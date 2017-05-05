package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate data from the forms of working with projects
 *
 * @author Q-NZA
 */
public class ProjectDataValidator extends Validator {

    private static final String NAME = "Название";
    private static final String SHORT_NAME = "Сокращённое название";
    private static final String ID = "id";
    private static final String NAME_FIELD = "name";
    private static final String SHORT_NAME_FIELD = "shortName";

    /**
     * Validate project information
     * 
     * @param parameters project data from the form
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();
        validateId(parameters);
        validateName(parameters, errors);
        validateShortName(parameters, errors);

        return errors;
    }

    private void validateShortName(Map<String, Object> parameters, List<String> errors) {
        validateFieldEmpty(parameters.get(SHORT_NAME_FIELD), SHORT_NAME, errors);
        validateFieldLength(parameters.get(SHORT_NAME_FIELD), SHORT_NAME, errors, 20);
    }

    private void validateName(Map<String, Object> parameters, List<String> errors) {
        validateFieldEmpty(parameters.get(NAME_FIELD), NAME, errors);
        validateFieldLength(parameters.get(NAME_FIELD), NAME, errors, 20);
    }

    private void validateId(Map<String, Object> parameters) {
        parseIntegerParams(ID, parameters);
    }


}
