package com.qulix.zakrevskynp.trainingtask.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "errorHandlerServlet", urlPatterns = {"/*"})
public class ErrorHandler implements Filter {
    
    private List<String> errors = new ArrayList<>();
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        }
        catch (Exception e) {
            Logger logger = Logger.getLogger(e.getStackTrace()[0].getClassName());
            logger.log(Level.SEVERE, e.getMessage());
            errors.clear();
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("personsList.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
