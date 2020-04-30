package com.mainacad.filters;

import com.mainacad.dao.UserDAO;
import com.mainacad.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter("/user")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String login = servletRequest.getParameter("login");
        String password = servletRequest.getParameter("password");
        User user = UserDAO.getByLoginAndPassword(login, password);
        RequestDispatcher dispatcher;
        if (user != null) {
            if (user.getLogin().equals("sergeyraychenko@gmail.com") && user.getPassword().equals("asdsadad")) {
                 dispatcher = servletRequest.getRequestDispatcher("/jsp/admin.jsp");
            } else {
                 dispatcher = servletRequest.getRequestDispatcher("/user");
            }
        }else {
            servletRequest.setAttribute("errorMsg", "Problem with registration!");
             dispatcher = servletRequest.getRequestDispatcher("/registration.jsp");
        }
        dispatcher.forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
