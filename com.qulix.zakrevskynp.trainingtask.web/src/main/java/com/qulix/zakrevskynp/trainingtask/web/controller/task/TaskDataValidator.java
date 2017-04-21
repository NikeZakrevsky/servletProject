package com.qulix.zakrevskynp.trainingtask.web.controller.task;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.zakrevskynp.trainingtask.web.LoggingFactory;
import com.qulix.zakrevskynp.trainingtask.web.controller.Validator;

/**
 * Validate data from forms for working with tasks
 * @author Q-NZA
 */
public class TaskDataValidator extends Validator {

    private static Logger logger = LoggingFactory.getLogger();
    private Map<String, Object> parameters;
    private List<String> errors = new ArrayList<>();
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String START_DATE_ERROR = "Дата начала : неверный формат";
    private static final String END_DATE_ERROR = "Дата окончания : неверный формат";
    private static final String END_BEFORE_START_ERROR = "Дата окончания должна быть раньше даты начала";
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

    /**
     * Validate task information
     * @param parameters form parameters for validation
     * @return list of errors
     */
    public List<String> validate(Map<String, Object> parameters) {
        this.parameters = parameters;
        java.util.Date startDate = validateDate(START_DATE_FIELD, START_DATE_ERROR);
        java.util.Date endDate = validateDate(END_DATE_FIELD, END_DATE_ERROR);
        validateEndDateBeforeStartDate(startDate, endDate, END_BEFORE_START_ERROR);
        validateFieldEmpty(this.parameters.get(NAME_FIELD), NAME, errors);
        validateFieldLength(this.parameters.get(NAME_FIELD), NAME, errors, 20);
        validateFieldEmpty(this.parameters.get(STATUS_FIELD), STATUS, errors);
        validateFieldLength(this.parameters.get(STATUS_FIELD), STATUS, errors, 20);
        validateFieldEmpty(this.parameters.get(WORK_TIME_FIELD), JOB, errors);
        validateFieldLength(this.parameters.get(WORK_TIME_FIELD), JOB, errors, 8);
        validateFieldNumbers(parameters.get(WORK_TIME_FIELD), JOB, errors);
        if (this.parameters.get(PROJECT_ID1_FIELD) != null) {
            this.parameters.put(PROJECT_ID_FIELD, this.parameters.get(PROJECT_ID1_FIELD));
        }
        parseIntegerParams(PROJECT_ID_FIELD, this.parameters);
        parseIntegerParams(PERSON_ID_FIELD, this.parameters);
        parseIntegerParams(ID, this.parameters);
        parseFloatParams(WORK_TIME_FIELD, this.parameters);
        if (parameters.get(WORK_TIME_FIELD) != null) {
            parameters.put(WORK_TIME_FIELD, Duration.ofMinutes((long) (int)
                    (Float.parseFloat(parameters.get(WORK_TIME_FIELD).toString()) * 60)));
        }
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
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
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
}
