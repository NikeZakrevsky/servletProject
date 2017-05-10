package com.qulix.zakrevskynp.trainingtask.web.controller.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 * Filter to set the encoding of the request
 *
 * @author Q-NZA
 */
@WebFilter(filterName = "encodingServlet", urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * Sets character encoding
     *
     * @param request @{({@link ServletRequest}} object
     * @param response @{({@link ServletResponse}} object
     * @param filter @{{@link FilterChain}} object
     * @throws IOException throws while forwarding request
     * @throws ServletException throws while forwarding request
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException,
        ServletException {

        request.setCharacterEncoding("UTF-8");
        filter.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
