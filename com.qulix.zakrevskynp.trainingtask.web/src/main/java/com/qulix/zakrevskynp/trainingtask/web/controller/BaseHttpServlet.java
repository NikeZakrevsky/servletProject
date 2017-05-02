package com.qulix.zakrevskynp.trainingtask.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Custom servlet with common methods for controllers
 *
 * @author Q-NZA
 */
public class BaseHttpServlet extends HttpServlet {

    /**
     * Convert parameters from request to map
     *
     * @param request @{{@link HttpServletRequest}} object
     * @return map with request parameters
     */
    protected Map<String, Object> getParametersFromRequest(HttpServletRequest request) {
        List<String> parametersNames = Collections.list(request.getParameterNames());
        return parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));
    }

    /**
     * Cast Object to List<Task> for avoiding unchecked cast warning
     *
     * @param var object for casting
     * @return list of tasks
     */
    protected List<Task> getItems(Object var) {
        if (var == null) {
            return null;
        }
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < ((List<?>)var).size(); i++) {
            Object item = ((List<?>) var).get(i);
            if (item instanceof Task) {
                result.add((Task) item);
            }
        }
        return result;
    }

}