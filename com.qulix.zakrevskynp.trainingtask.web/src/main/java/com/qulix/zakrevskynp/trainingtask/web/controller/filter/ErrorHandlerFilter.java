package com.qulix.zakrevskynp.trainingtask.web.controller.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import com.qulix.zakrevskynp.trainingtask.web.LoggingFactory;
import com.qulix.zakrevskynp.trainingtask.web.controller.Attribute;

/**
 * The filter intercepts and handles exceptions.
 *
 * @author Q-NZA
 */
@WebFilter(filterName = "errorHandlerServlet", urlPatterns = {"/*"})
public class ErrorHandlerFilter implements Filter {
    private static final Logger LOGGER = LoggingFactory.getLogger();
    private static final String DATABASE_ERROR = "Ошибка при чтении данных";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * The method intercepts, logs and displays error to the user .
     *
     * @param request @{({@link ServletRequest}} object.
     * @param response @{({@link ServletResponse}} object.
     * @param filter @{{@link FilterChain}} object.
     * @throws IOException throws while forwarding request.
     * @throws ServletException throws while forwarding request.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException,
        ServletException {

        try {
            filter.doFilter(request, response);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception: ", e);
            request.setAttribute("error", DATABASE_ERROR);
            request.getRequestDispatcher(Attribute.PERSONS_LIST_VIEW).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
