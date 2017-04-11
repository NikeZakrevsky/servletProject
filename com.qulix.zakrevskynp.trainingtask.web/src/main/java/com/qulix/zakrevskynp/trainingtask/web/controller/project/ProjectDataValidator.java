package com.qulix.zakrevskynp.trainingtask.web.controller.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate project form data
 * @author Q-NZA
 */
public class ProjectDataValidator extends Validator{

    private static final String NAME_ERROR = "Неверное поле название";
    private static final String SHORT_NAME_ERROR = "Неверное поле сокращённое название";

    /**
     * Validate project information
     * @param parameters project form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        parseIntegerParams("id", parameters);
        validateField(parameters.get("name"), NAME_ERROR, errors);
        validateField(parameters.get("shortName"), SHORT_NAME_ERROR, errors);
        
        return errors;
    }
}
