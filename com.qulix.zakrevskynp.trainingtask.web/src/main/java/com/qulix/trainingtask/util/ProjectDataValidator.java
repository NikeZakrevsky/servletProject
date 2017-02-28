package com.qulix.trainingtask.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Validate project form data
 */
public class ProjectDataValidator {
    public List<String> validate(String name, String shortName, String description) {
        List<String> errors = new ArrayList<>();

        if (name.equals("")) {
            errors.add("Название не должно быть пустым");
        }
        if (name.length() > 20) {
            errors.add("Название слишком длинное");
        }

        if (shortName.equals("")) {
            errors.add("Сокращённое название не должно быть пустым");
        }
        if (shortName.length() > 20) {
            errors.add("Сокращённое название слишком длинное");
        }

        if (description.equals("")) {
            errors.add("Описание не должно быть пустым");
        }
        if (description.length() > 20) {
            errors.add("Описание слишком длинное");
        }

        return errors;
    }
}
