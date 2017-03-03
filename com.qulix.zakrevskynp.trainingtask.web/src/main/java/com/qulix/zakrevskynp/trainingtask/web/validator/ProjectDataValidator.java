package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;

/**
 * Validate project form data
 * @author Q-NZA
 */
public class ProjectDataValidator {
    public List<String> validate(Project project) {
        List<String> errors = new ArrayList<>();

        if (project.getName().equals("")) {
            errors.add("Название не должно быть пустым");
        }
        if (project.getName().length() > 20) {
            errors.add("Название слишком длинное");
        }

        if (project.getShortName().equals("")) {
            errors.add("Сокращённое название не должно быть пустым");
        }
        if (project.getShortName().length() > 20) {
            errors.add("Сокращённое название слишком длинное");
        }

        if (project.getDescription().length() > 20) {
            errors.add("Описание слишком длинное");
        }

        return errors;
    }
}
