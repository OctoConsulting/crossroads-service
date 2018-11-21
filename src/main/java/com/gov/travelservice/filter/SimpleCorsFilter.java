package com.gov.travelservice.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCorsFilter implements Filter {

    /**
     The SimpleCorsFilter will add the necessary response headers to a server response, allowing cross origin communication
     @param req the server request
     @param res the server's response
     **/
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if ("OPTIONS".equals(request.getMethod())) {
        	response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, res);
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}
}

