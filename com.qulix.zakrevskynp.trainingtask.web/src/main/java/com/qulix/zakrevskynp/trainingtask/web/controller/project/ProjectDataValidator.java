package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate data from the forms of working with projects
 *
 * @author Q-NZA
 */
public class ProjectDataValidator extends Validator {

    private static final String NAME = "Название";
    private static final String SHORT_NAME = "Сокращённое название";
    private static final String NAME_FIELD = "name";
    private static final String SHORT_NAME_FIELD = "shortName";

    /**
     * Validate project information
     * 
     * @param parameters project data from the form
     * @return list of errors
     */
    public List<String> validate(HttpServletRequest parameters) {
        List<String> errors = new ArrayList<>();

        validateName(parameters.getParameter(NAME_FIELD), errors);
        validateShortName(parameters.getParameter(SHORT_NAME_FIELD), errors);

        return errors;
    }

    private void validateShortName(Object parameter, List<String> errors) {
        validateFieldEmpty(parameter, SHORT_NAME, errors);
        validateFieldLength(parameter, SHORT_NAME, errors, 25);
    }

    private void validateName(Object parameter, List<String> errors) {
        validateFieldEmpty(parameter, NAME, errors);
        validateFieldLength(parameter, NAME, errors, 25);
    }
}
