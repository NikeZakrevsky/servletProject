package com.qulix.zakrevskynp.trainingtask.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Base servlet with common methods for controllers
 *
 * @author Q-NZA
 */
public class BaseHttpServlet extends HttpServlet {
    /**
     * Cast Object to List<Task> for avoiding unchecked cast warning
     *
     * @param object object for casting
     * @return list of tasks
     */
    protected List<Task> getItems(Object object) {
        if (object == null) {
            return null;
        }

        List<Task> result = new ArrayList<>();
        for (int i = 0; i < ((List<?>) object).size(); i++) {
            Object item = ((List<?>) object).get(i);
            if (item instanceof Task) {
                result.add((Task) item);
            }
        }

        return result;
    }
}