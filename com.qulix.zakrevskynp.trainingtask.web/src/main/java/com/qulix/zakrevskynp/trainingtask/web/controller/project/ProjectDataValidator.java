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

    /**
     * Validate project information
     * @param parameters project form data
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        if (!parameters.get("id").equals("")) {
            parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
        }
        else {
            parameters.put("id", null);
        }

        validateField(parameters.get("name"), "Неверное поле название", errors);
        validateField(parameters.get("shortName"), "Неверное поле сокращённое название", errors);
        
        return errors;
    }
}
