package com.qulix.trainingtask.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Validate person form data
 */
public class PersonDataValidator {

    /**
     * Validate person information
     * @param fname first name
     * @param sname middle name
     * @param lname last name
     * @param position position of person
     * @return list of errors
     */
    public List<String> validate(String fname, String sname, String lname, String position) {
        List<String> errors = new ArrayList<>();

        if (fname.equals("")) {
            errors.add("First name must be not empty");
        }
        if (fname.length() > 20) {
            errors.add("First name is too long");
        }

        if (sname.equals("")) {
            errors.add("Second name must be not empty");
        }
        if (sname.length() > 20) {
            errors.add("Second name is too long");
        }

        if (lname.equals("")) {
            errors.add("Last name must be not empty");
        }
        if (lname.length() > 20) {
            errors.add("Last name is too long"); }

        if (position.equals("")) {
            errors.add("Position must be not empty");
        }
        if (position.length() > 20) {
            errors.add("Position is too long");
        }

        return errors;
    }
}
