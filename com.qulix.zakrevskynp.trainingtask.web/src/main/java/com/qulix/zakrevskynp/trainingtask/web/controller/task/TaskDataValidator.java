package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
    private static final String STATUS_FIELD = "taskStatus";
    private static final String BETWEEN_ERROR = "Время работы больше, чем между датами";
    private Date startDate;
    private Date endDate;

    /**
     * Validate task information
     *
     * @param parameters form parameters for validation
     * @return list of errors
     */
    public List<String> validate(HttpServletRequest parameters) {
        List<String> errors = new ArrayList<>();
        
        validateName(parameters.getParameter(NAME_FIELD), errors);
        validateStatus(parameters.getParameter(STATUS_FIELD), errors);
        boolean isDatesValid = validateDates(parameters.getParameter(START_DATE_FIELD), parameters.getParameter(END_DATE_FIELD),
            errors);
        boolean isWorkTimeValid = validateWorkTime(parameters.getParameter(WORK_TIME_FIELD), errors);
        if (isWorkTimeValid && isDatesValid) {
            validateDateTime(startDate, endDate, parameters.getParameter(WORK_TIME_FIELD), BETWEEN_ERROR, errors);
        }

        return errors;
    }

    private boolean validateWorkTime(String workTime, List<String> errors) {
        boolean isEmpty = validateFieldEmpty(workTime, JOB, errors);
        boolean isLengthValid = validateFieldLength(workTime, JOB, errors, 8);
        boolean isNumbersValid = validateFieldNumbers(workTime, JOB, errors);

        return isEmpty && isLengthValid && isNumbersValid;
    }

    private boolean validateDates(String startDateField, String endDateField, List<String> errors) {
        startDate = validateDate(startDateField, START_DATE_ERROR, errors);
        endDate = validateDate(endDateField, END_DATE_ERROR, errors);

        validateEndDateBeforeStartDate(startDate, endDate, END_BEFORE_START_ERROR, errors);

        return startDate != null && endDate != null;
    }

    private void validateStatus(Object parameter, List<String> errors) {
        validateFieldEmpty(parameter, STATUS, errors);
        validateFieldLength(parameter, STATUS, errors, 20);
    }

    private void validateName(Object parameter, List<String> errors) {
        validateFieldEmpty(parameter, NAME, errors);
        validateFieldLength(parameter, NAME, errors, 20);
    }
}
