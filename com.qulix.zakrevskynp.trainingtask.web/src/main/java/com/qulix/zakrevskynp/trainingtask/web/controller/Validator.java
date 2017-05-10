package com.qulix.zakrevskynp.trainingtask.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Validates data from forms
 *
 * @author Q-NZA
 */
public abstract class Validator {

    private static final String EMPTY_ERROR = "Укажите значение поля \"%s\"";
    private static final String LENGTH_ERROR = "Длина поля \"%s\" не должна превышать 20 символов";
    private static final String SYMBOLS_ERROR = "Поле \"%s\" должно содержать только латинские и русские буквы";
    private static final String NUMBER_ERROR = "Поле \"%s\" должно содержать только цифры";
    private static final String REGEX = "^[a-zA-ZА-Яа-яёЁ\\s]*$";
    private static final String REGEX1 = "\\d{1,8}(.\\d)?";
    private Predicate<Object> testEmpty = e -> e == null || e.equals("");
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Field length validation
     *
     * @param field field for validation
     * @param fieldName field name for validation
     * @param errors list of the errors
     * @param fieldLength allowable field length
     */
    protected boolean validateFieldLength(Object field, String fieldName, List<String> errors, int fieldLength) {
        if (field.toString().length() > fieldLength) {
            errors.add(String.format(LENGTH_ERROR, fieldName));
            return false;
        }
        return true;
    }

    /**
     * Field empties validation
     *
     * @param field field for validation
     * @param fieldName field name for validation
     * @param errors list of the errors
     */
    protected boolean validateFieldEmpty(Object field, String fieldName, List<String> errors) {
        if (testEmpty.test(field)) {
            errors.add(String.format(EMPTY_ERROR, fieldName));
            return false;
        }
        return true;
    }

    /**
     * Character field validation
     *
     * @param field field for validation
     * @param fieldName field name for validation
     * @param errors list of the errors
     */
    protected void validateFieldSymbols(Object field, String fieldName, List<String> errors) {
        if (!field.toString().matches(REGEX)) {
            errors.add(String.format(SYMBOLS_ERROR, fieldName));
        }
    }

    /**
     * Number field validation
     *
     * @param field field for validation
     * @param fieldName field name for validation
     * @param errors list of the errors
     */
    protected boolean validateFieldNumbers(Object field, String fieldName, List<String> errors) {
        if (!field.toString().equals("") && !field.toString().matches(REGEX1)) {
            errors.add(String.format(NUMBER_ERROR, fieldName));

            return false;
        }

        return true;
    }

    protected Date validateDate(Object field, String error, List<String> errors) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        Date date = null;
        try {
            date = dateFormat.parse(field.toString());
        } catch (ParseException e) {
            errors.add(error);
        }

        return date;
    }

    protected void validateEndDateBeforeStartDate(java.util.Date startDate, java.util.Date endDate, String error, List<String>
        errors) {
        if (startDate != null && endDate != null) {
            if (!startDate.before(endDate) && !startDate.equals(endDate)) {
                errors.add(error);
            }
        }
    }

    protected void validateDateTime(java.util.Date startDate, java.util.Date endDate, String workTime, String error,
        List<String> errors) {
        Duration duration = Duration.ofMinutes((long) (int) (Float.parseFloat(workTime) * 60));
        long diff = endDate.getTime() - startDate.getTime();
        if (duration.toMinutes() / 60.0 - (TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) + 24) > 0) {
            errors.add(error);
        }
    }
}
