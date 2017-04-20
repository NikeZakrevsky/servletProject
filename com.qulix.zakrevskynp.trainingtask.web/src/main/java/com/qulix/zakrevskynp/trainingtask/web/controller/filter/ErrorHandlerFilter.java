package com.qulix.zakrevskynp.trainingtask.web.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import com.qulix.zakrevskynp.trainingtask.web.LoggingFactory;
import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;

/**
 * Filter for handling and process exceptions
 * @author Q-NZA
 */
@WebFilter(filterName = "errorHandlerServlet", urlPatterns = {"/*"})
public class ErrorHandlerFilter implements Filter {
    
    private List<String> errors = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Process exceptions and log it
     * @param request @{({@link ServletRequest}} object
     * @param response @{({@link ServletResponse}} object
     * @param ch @{{@link FilterChain}} object
     * @throws IOException throws while forwarding request
     * @throws ServletException throws while forwarding request
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain ch) throws IOException, ServletException {
        try {
            ch.doFilter(request, response);
        }
        catch (Exception e) {
            Logger logger = LoggingFactory.getLogger();
            logger.log(Level.SEVERE, "Exception: " + e);
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher(Attribute.PERSONS_LIST_VIEW).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
