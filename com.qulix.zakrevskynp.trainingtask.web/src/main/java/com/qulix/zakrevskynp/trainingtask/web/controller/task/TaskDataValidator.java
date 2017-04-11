package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate task form data
 * @author Q-NZA
 */
public class TaskDataValidator extends Validator{
    private static Logger logger = Logger.getLogger(TaskDataValidator.class.getName());
    private Map<String, Object> parameters;
    private List<String> errors = new ArrayList<>();

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String regex = "\\d+";

    private static final String START_DATE_ERROR = "Неверная дата начала";
    private static final String END_DATE_ERROR = "Неверная дата окончания";
    private static final String END_BEFORE_START_ERROR = "Дата окончания должна быть раньше даты начала";
    private static final String NAME_ERROR = "Неверное поле название";
    private static final String STATUS_ERROR = "Неверное поле статус";
    private static final String WORK_TIME_ERROR = "Неверное время работы";
    /**
     * Validate task information
     * @param parameters form parameters for validation
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        this.parameters = parameters;

        java.util.Date startDate = validateDate("startDate", START_DATE_ERROR);
        java.util.Date endDate = validateDate("endDate", END_DATE_ERROR);

        validateEndDateBeforeStartDate(startDate, endDate, END_BEFORE_START_ERROR);

        validateField(this.parameters.get("name"), NAME_ERROR, errors);
        validateField(this.parameters.get("status"), STATUS_ERROR, errors);

        if (!this.parameters.get("time").toString().matches(regex) || this.parameters.get("time").toString().length() > 8) {
            errors.add(WORK_TIME_ERROR);
        }

        if (parameters.get("projectId1") != null) {
            parameters.put("projectId", parameters.get("projectId1"));
        }

        parseIntegerParams("projectId");
        parseIntegerParams("personId");
        parseIntegerParams("id");
        parseIntegerParams("time");

        return errors;
    }

    private void validateEndDateBeforeStartDate(java.util.Date startDate, java.util.Date endDate, String error) {
        if (startDate != null && endDate != null) {
            if (!startDate.before(endDate)) {
                errors.add(error);
            }
        }
    }

    private java.util.Date validateDate(String field, String error) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        java.util.Date date = null;
        try {
            date = sdf.parse(parameters.get(field).toString());
            Date date1 = new Date(date.getTime());
            parameters.put(field, date1);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.getMessage());
            errors.add(error);
        }
        return date;
    }

    private void parseIntegerParams(String field) {
        if (!parameters.get(field).toString().equals("")) {
            parameters.put(field, Integer.parseInt(parameters.get(field).toString()));
        }
        else {
            parameters.put(field, null);
        }
    }
}
