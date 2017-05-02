package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate data from the forms of working with tasks
 *
 * @author Q-NZA
 */
public class TaskDataValidator extends Validator {

    private static final String START_DATE_ERROR = "Неверный формат поля \"Дата начала\"";
    private static final String END_DATE_ERROR = "Неверный формат поля \"Дата окончания\"";
    private static final String END_BEFORE_START_ERROR = "Дата начала должна быть раньше даты окончания";
    private static final String NAME = "Название";
    private static final String STATUS = "Статус";
    private static final String JOB = "Работа(часы)";
    private static final String START_DATE_FIELD = "startDate";
    private static final String NAME_FIELD = "name";
    private static final String END_DATE_FIELD = "endDate";
    private static final String WORK_TIME_FIELD = "workTime";
    private static final String ID = "id";
    private static final String PROJECT_ID_FIELD = "projectId";
    private static final String PROJECT_ID1_FIELD = "projectId1";
    private static final String STATUS_FIELD = "taskStatus";
    private static final String PERSON_ID_FIELD = "personId";
    private static final String BETWEEN_ERROR = "Время работы больше, чем между датами";

    /**
     * Validate task information
     *
     * @param parameters form parameters for validation
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        List<String> errors = new ArrayList<>();
        validateIds(parameters);
        validateName(parameters, errors);
        validateStatus(parameters, errors);
        java.util.Date startDate = validateStartDate(parameters, errors);
        java.util.Date endDate = validateEndDate(parameters, errors);
        validateEndDateBeforeStartDate(startDate, endDate, END_BEFORE_START_ERROR, errors);
        boolean isWorkTimeValid = validateWorkTime(parameters, errors);
        if (isWorkTimeValid && startDate != null && endDate != null) {
            validateDateTime(startDate, endDate, (Duration) parameters.get(WORK_TIME_FIELD), BETWEEN_ERROR, errors);
        }
        return errors;
    }

    private java.util.Date validateEndDate(Map<String, Object> parameters, List<String> errors) {
        java.util.Date date = validateDate(parameters.get(END_DATE_FIELD), END_DATE_ERROR, errors);
        if (date != null) {
            parameters.put(END_DATE_FIELD, new Date(date.getTime()));
        }
        return date;
    }

    private java.util.Date validateStartDate(Map<String, Object> parameters, List<String> errors) {
        java.util.Date date = validateDate(parameters.get(START_DATE_FIELD), START_DATE_ERROR, errors);
        if (date != null) {
            parameters.put(START_DATE_FIELD, new Date(date.getTime()));
        }
        return date;
    }

    private void validateIds(Map<String, Object> parameters) {
        parseIntegerParams(PROJECT_ID_FIELD, parameters);
        parseIntegerParams(PERSON_ID_FIELD, parameters);
        parseIntegerParams(ID, parameters);
        if (parameters.get(PROJECT_ID1_FIELD) != null) {
            parseIntegerParams(PROJECT_ID1_FIELD, parameters);
            parameters.put(PROJECT_ID_FIELD, parameters.get(PROJECT_ID1_FIELD));
        }
    }

    private boolean validateWorkTime(Map<String, Object> parameters, List<String> errors) {
        validateFieldEmpty(parameters.get(WORK_TIME_FIELD), JOB, errors);
        validateFieldLength(parameters.get(WORK_TIME_FIELD), JOB, errors, 8);
        parameters.put("workTimeString", parameters.get(WORK_TIME_FIELD));
        if (validateFieldNumbers(parameters.get(WORK_TIME_FIELD), JOB, errors)) {
            parseFloatParams(WORK_TIME_FIELD, parameters);
            if (parameters.get(WORK_TIME_FIELD) != null) {
                parameters.put(WORK_TIME_FIELD, Duration.ofMinutes((long) (int) (Float.parseFloat(parameters.get(WORK_TIME_FIELD)
                        .toString()) * 60)));
            }
            return true;
        }
        else {
            parameters.put(WORK_TIME_FIELD, null);
            return false;
        }
    }

    private void validateStatus(Map<String, Object> parameters, List<String> errors) {
        validateFieldEmpty(parameters.get(STATUS_FIELD), STATUS, errors);
        validateFieldLength(parameters.get(STATUS_FIELD), STATUS, errors, 20);
    }

    private void validateName(Map<String, Object> parameters, List<String> errors) {
        validateFieldEmpty(parameters.get(NAME_FIELD), NAME, errors);
        validateFieldLength(parameters.get(NAME_FIELD), NAME, errors, 20);
    }

}
