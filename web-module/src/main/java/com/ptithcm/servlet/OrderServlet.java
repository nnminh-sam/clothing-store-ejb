package com.ptithcm.servlet;

import com.ptithcm.ejb.entity.Order;
import com.ptithcm.ejb.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/orders")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        List<Order> orders = user.getOrders();
        System.out.println(">>>> Debug: " + orders.size());
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/WEB-INF/orders.jsp").forward(request, response);
    }
}
