package com.qulix.zakrevskynp.trainingtask.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 * Handle and process errors
 * @author Q-NZA
 */
@WebFilter(filterName = "errorHandlerServlet", urlPatterns = {"/*"})
public class ErrorHandler implements Filter {
    
    private List<String> errors = new ArrayList<>();

    /**
     * Initialize filter
     * @param filterConfig @{{@link FilterConfig}} object
     * @throws ServletException servlet exception
     */
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
            request.setCharacterEncoding("UTF-8");
            ch.doFilter(request, response);
        }
        catch (Exception e) {
            Logger logger = Logger.getLogger(e.getStackTrace()[0].getClassName());
            logger.log(Level.SEVERE, e.toString());
            errors.clear();
            errors.add(e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", errors);
            request.getRequestDispatcher("view/personsList.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
