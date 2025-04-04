package com.ptithcm.servlet;

import com.ptithcm.ejb.entity.User;
import com.ptithcm.ejb.service.AuthServiceRemote;
import com.ptithcm.ejb.service.UserServiceRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({ "/login", "/register", "/logout" })
public class AuthServlet extends HttpServlet {
    @EJB
    private AuthServiceRemote authService;

    @EJB
    private UserServiceRemote userService;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String jspPath = "/login".equals(path) ? "/WEB-INF/home.jsp" : "/WEB-INF/register.jsp";
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            if ("/login".equals(path)) {
                processLogin(request, response);
            } else if ("/register".equals(path)) {
                processRegistration(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }
    }

    private void processLogin(
            HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email.isBlank()) {
            throw new Exception("Email is required");
        }
        if (password.isBlank()) {
            throw new Exception("Password is required");
        }

        User user = authService.login(email, password);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/home");
    }

    private void processRegistration(
            HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (email.isBlank()) {
            throw new Exception("Email is required");
        }
        if (password.isBlank()) {
            throw new Exception("Password is required");
        }
        if (confirmPassword.isBlank()) {
            throw new Exception("Confirm password is required");
        }

        if (!password.equals(confirmPassword)) {
            throw new Exception("Passwords do not match");
        }

        User user = userService.create(email, password, email);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/home");
    }
}

