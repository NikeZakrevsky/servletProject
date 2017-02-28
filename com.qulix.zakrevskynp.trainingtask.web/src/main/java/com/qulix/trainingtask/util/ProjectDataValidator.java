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
            errors.add("Name must be not empty");
        }
        if (name.length() > 20) {
            errors.add("Name is too long");
        }

        if (shortName.equals("")) {
            errors.add("Short name must be not empty");
        }
        if (shortName.length() > 20) {
            errors.add("Short name is too long");
        }

        if (description.equals("")) {
            errors.add("Description must be not empty");
        }
        if (description.length() > 20) {
            errors.add("Description is too long");
        }

        return errors;
    }
}
