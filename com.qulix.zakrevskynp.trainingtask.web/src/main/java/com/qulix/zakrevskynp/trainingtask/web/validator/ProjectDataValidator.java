package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validate project form data
 * @author Q-NZA
 */
public class ProjectDataValidator {
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();

        if (parameters.get("name").toString().equals("")) {
            errors.add("Название не должно быть пустым");
        }
        if (parameters.get("name").toString().length() > 20) {
            errors.add("Название слишком длинное");
        }

        if (parameters.get("shortName").toString().equals("")) {
            errors.add("Сокращённое название не должно быть пустым");
        }
        if (parameters.get("shortName").toString().length() > 20) {
            errors.add("Сокращённое название слишком длинное");
        }

        if (parameters.get("description").toString().length() > 20) {
            errors.add("Описание слишком длинное");
        }

        return errors;
    }
}
