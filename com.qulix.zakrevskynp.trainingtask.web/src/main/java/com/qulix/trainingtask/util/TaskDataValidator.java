package com.qulix.trainingtask.util;

import com.qulix.trainingtask.model.Person;
import com.qulix.trainingtask.model.Project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Validate task form data
 */
public class TaskDataValidator {

    private String regex = "\\d+";

    public List<String> validate(String name, String time, String start_date, String end_date, String status, String projectId, String personId, boolean isFromProjectForm) {
        List<String> errors = new ArrayList<String>();

        if (isDateValid(start_date, "yyyy-MM-dd")) {
            errors.add("Неверная дата начала");
        }
        if (isDateValid(end_date, "yyyy-MM-dd")) {
            errors.add("Неверная дата окончания");
        }

        if (start_date.compareTo(end_date) == 1) {
            errors.add("Дата окончания должна быть раньше даты начала");
        }

        if (name == null || name.equals("")) {
            errors.add("Название не должно быть пустым");
        }
        if (name != null && name.length() > 20) {
            errors.add("Название слишком длинное");
        }

        if (status == null || status.equals("")) {
            errors.add("Статус не должен быть пустым");
        }
        if (status != null && status.length() > 20) {
            errors.add("Статус слишком длинный");
        }

        if (!time.matches(regex)) {
            errors.add("Неверное время работы");
        }

        return errors;
    }

    private boolean isPersonInDB(List<Person> personList, int personId) {
        for (Person person : personList) {
            if (person.getId() == personId) {
                return true;
            }
        }
        return false;
    }

    private boolean isProjectInDB(List<Project> projectList, int projectId) {
        for (Project project : projectList) {
            if (project.getId() == projectId) {
                return true;
            }
        }
        return false;
    }

    private boolean isDateValid(String dateToValidate, String dateFormat) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }
}
