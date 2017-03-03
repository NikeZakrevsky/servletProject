package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validate task form data
 * @author Q-NZA
 */
public class TaskDataValidator {

    public List<String> validate(Map<String, Object> parameters) {
        String dateFormat = "yyyy-MM-dd";
        String regex = "\\d+";
        List<String> errors = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            Date date = new java.sql.Date(sdf.parse(parameters.get("start_date").toString()).getTime());
            parameters.put("start_date", date);
        } catch (ParseException e) {
            errors.add("Неверная дата начала");
        }

        try {
            Date date = new java.sql.Date(sdf.parse(parameters.get("end_date").toString()).getTime());
            parameters.put("end_date", date);
        } catch (ParseException e) {
            errors.add("Неверная дата окончания");
        }

        if (parameters.get("end_date").toString().compareTo(parameters.get("end_date").toString()) == 1) {
            errors.add("Дата окончания должна быть раньше даты начала");
        }

        if (parameters.get("name") == null || parameters.get("name").equals("")) {
            errors.add("Название не должно быть пустым");
        }
        if (parameters.get("name") != null && parameters.get("name").toString().length() > 20) {
            errors.add("Название слишком длинное");
        }

        if (parameters.get("status") == null || parameters.get("status").equals("")) {
            errors.add("Статус не должен быть пустым");
        }
        if (parameters.get("status") != null && parameters.get("status").toString().length() > 20) {
            errors.add("Статус слишком длинный");
        }

        if (!parameters.get("time").toString().matches(regex)) {
            errors.add("Неверное время работы");
        }

        if (!parameters.get("projectId").toString().equals("")) {
            parameters.put("projectId", Integer.parseInt(parameters.get("projectId").toString()));
        }
        else {
            parameters.put("projectId", null);
        }

        if (!parameters.get("personId").toString().equals("")) {
            parameters.put("personId", Integer.parseInt(parameters.get("personId").toString()));
        }
        else {
            parameters.put("personId", null);
        }
        
        return errors;
    }
}
