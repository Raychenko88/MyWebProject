package com.mainacad.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String login = servletRequest.getParameter("login");
        String password = servletRequest.getParameter("password");

//        if (){
//            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/jsp/admin.jsp");
//        }else {
//
//        }

    }

    @Override
    public void destroy() {

    }
}
