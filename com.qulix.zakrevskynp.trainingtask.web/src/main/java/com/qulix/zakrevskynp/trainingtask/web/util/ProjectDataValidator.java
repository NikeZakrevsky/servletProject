package com.qulix.zakrevskynp.trainingtask.web.util;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Validate project form data
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

        if (project.getShortName().equals("")) {
            errors.add("Описание не должно быть пустым");
        }
        if (project.getShortName().length() > 20) {
            errors.add("Описание слишком длинное");
        }

        return errors;
    }
}
