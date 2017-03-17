package com.qulix.zakrevskynp.trainingtask.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Extend HttpServlet
 *
 * @author Q-NZA
 */
public class CustomServlet extends HttpServlet {

    protected Map<String, Object> getParametersFromRequest(HttpServletRequest request) {
        List<String> parametersNames = Collections.list(request.getParameterNames());
        return parametersNames.stream().collect(Collectors.toMap(x -> x, request::getParameter));
    }

}