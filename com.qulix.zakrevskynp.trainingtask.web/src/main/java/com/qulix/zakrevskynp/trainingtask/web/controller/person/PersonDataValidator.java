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
    private static final String MIDDLE_NAME_ERROR = "Неверное поле имя";
    private static final String LAST_NAME_ERROR = "Неверное поле имя";
    private static final String POSITION_ERROR = "Неверное поле имя";

    /**
     * Validate person information
     * @param parameters person form data
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
        validateField(parameters.get("firstName"), FIRST_NAME_ERROR, errors);
        validateField(parameters.get("middleName"), MIDDLE_NAME_ERROR, errors);
        validateField(parameters.get("lastName"), LAST_NAME_ERROR, errors);
        validateField(parameters.get("position"), POSITION_ERROR, errors);

        return errors;
    }
}
