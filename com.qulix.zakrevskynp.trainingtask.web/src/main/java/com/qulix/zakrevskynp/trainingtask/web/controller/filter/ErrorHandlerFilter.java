package com.qulix.zakrevskynp.trainingtask.web.controller.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import com.qulix.zakrevskynp.trainingtask.web.LoggingFactory;
import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;

/**
 * Filter for handling and processing exceptions
 *
 * @author Q-NZA
 */
@WebFilter(filterName = "errorHandlerServlet", urlPatterns = {"/*"})
public class ErrorHandlerFilter implements Filter {

    private static final Logger LOGGER = LoggingFactory.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Processing and logging exceptions
     *
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
            LOGGER.log(Level.SEVERE, "Exception: ", e);
            String error = e.getMessage();
            request.setAttribute("error", error);
            request.getRequestDispatcher(Attribute.PERSONS_LIST_VIEW).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
