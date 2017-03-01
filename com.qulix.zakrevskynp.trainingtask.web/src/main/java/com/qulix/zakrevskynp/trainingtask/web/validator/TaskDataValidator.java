package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Validate task form data
 * @author Q-NZA
 */
public class TaskDataValidator {
    private final String dateFormat = "yyyy-MM-dd";
    private final String regex = "\\d+";

    public List<String> validate(Task task) {
        List<String> errors = new ArrayList<>();

        if (isDateValid(task.getStartDate().toString())) {
            errors.add("Неверная дата начала");
        }
        if (isDateValid(task.getEndDate().toString())) {
            errors.add("Неверная дата окончания");
        }

        if (task.getStartDate().compareTo(task.getEndDate()) == 1) {
            errors.add("Дата окончания должна быть раньше даты начала");
        }

        if (task.getName() == null || task.getName().equals("")) {
            errors.add("Название не должно быть пустым");
        }
        if (task.getName() != null && task.getName().length() > 20) {
            errors.add("Название слишком длинное");
        }

        if (task.getStatus() == null || task.getStatus().equals("")) {
            errors.add("Статус не должен быть пустым");
        }
        if (task.getStatus() != null && task.getStatus().length() > 20) {
            errors.add("Статус слишком длинный");
        }

        if (!Integer.toString(task.getTime()).matches(regex)) {
            errors.add("Неверное время работы");
        }

        return errors;
    }

    private boolean isDateValid(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }
}
