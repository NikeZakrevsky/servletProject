package com.qulix.zakrevskynp.trainingtask.web.task;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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

        Predicate<Object> isEmptyAndLength = e -> e.equals("") || e.toString().length() > 20;

        sdf.setLenient(false);
        try {
            Date date = new java.sql.Date(sdf.parse(parameters.get("startDate").toString()).getTime());
            parameters.put("startDate", date);
        } catch (ParseException e) {
            errors.add("Неверная дата начала");
        }

        try {
            Date date = new java.sql.Date(sdf.parse(parameters.get("endDate").toString()).getTime());
            parameters.put("endDate", date);
        } catch (ParseException e) {
            errors.add("Неверная дата окончания");
        }

        if (parameters.get("endDate").toString().compareTo(parameters.get("endDate").toString()) == 1) {
            errors.add("Дата окончания должна быть раньше даты начала");
        }

        if (isEmptyAndLength.test(parameters.get("name"))) errors.add("Неверное поле название");
        if (isEmptyAndLength.test(parameters.get("status"))) errors.add("Неверное поле статус");

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
