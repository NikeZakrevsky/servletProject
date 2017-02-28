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
            errors.add("Имя не должно быть пустым");
        }
        if (fname.length() > 20) {
            errors.add("Имя слишком длинное");
        }

        if (sname.equals("")) {
            errors.add("Фамилия не должна быть пустой");
        }
        if (sname.length() > 20) {
            errors.add("Фамилия слишком длинная");
        }

        if (lname.equals("")) {
            errors.add("Отчество не должно быть пустым");
        }
        if (lname.length() > 20) {
            errors.add("Отчество слишком длинное"); }

        if (position.equals("")) {
            errors.add("Должность не должна быть пустой");
        }
        if (position.length() > 20) {
            errors.add("Должность слишком длинная");
        }

        return errors;
    }
}
