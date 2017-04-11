package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Validate task form data
 * @author Q-NZA
 */
public class TaskDataValidator {
    private static Logger logger = Logger.getLogger(TaskDataValidator.class.getName());

    /**
     * Validate task information
     * @param parameters form parameters for validation
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        String dateFormat = "yyyy-MM-dd";
        String regex = "\\d+";
        List<String> errors = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        Predicate<Object> isEmptyAndLength = e -> e.equals("") || e.toString().length() > 20;

        sdf.setLenient(false);

        java.util.Date startDate = null;
        try {
            startDate = sdf.parse(parameters.get("startDate").toString());
            Date date = new Date(startDate.getTime());
            parameters.put("startDate", date);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.getMessage());
            errors.add("Неверная дата начала");
        }

        java.util.Date endDate = null;
        try {
            endDate = sdf.parse(parameters.get("endDate").toString());
            Date date = new java.sql.Date(endDate.getTime());
            parameters.put("endDate", date);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.getMessage());
            errors.add("Неверная дата окончания");
        }

        if (startDate != null && endDate != null) {
            if (!startDate.before(endDate)) {
                errors.add("Дата окончания должна быть раньше даты начала");
            }
        }

        if (isEmptyAndLength.test(parameters.get("name"))) {
            errors.add("Неверное поле название");
        }
        if (isEmptyAndLength.test(parameters.get("status"))) {
            errors.add("Неверное поле статус");
        }

        if (!parameters.get("time").toString().matches(regex) || parameters.get("time").toString().length() > 8) {
            errors.add("Неверное время работы");
        }

        if (parameters.get("projectId1") != null) {
            parameters.put("projectId", parameters.get("projectId1"));
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

        if (!parameters.get("id").equals("")) {
            parameters.put("id", Integer.parseInt(parameters.get("id").toString()));
        }
        else {
            parameters.put("id", null);
        }
        
        if (!parameters.get("time").equals("")) {
            parameters.put("time", Integer.parseInt(parameters.get("time").toString()));
        }
        return errors;
    }
}
