package com.ptithcm.servlet;

import com.ptithcm.ejb.entity.Order;
import com.ptithcm.ejb.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/orders/detail")
public class OrderDetailServlet extends HttpServlet {

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

        String orderId = request.getParameter("id");

        if (orderId == null) {
            response.sendRedirect(request.getContextPath() + "/user/orders");
            return;
        }

        Order matchedOrder = null;

        try {
            int id = Integer.parseInt(orderId);
            for (Order order : orders) {
                if (order.getId() == id) {
                    matchedOrder = order;
                    break;
                }
            }

            if (matchedOrder == null) {
                response.sendRedirect(request.getContextPath() + "/user/orders");
                return;
            }

            request.setAttribute("order", matchedOrder);
            request.getRequestDispatcher("/WEB-INF/order-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/user/orders");
        }
    }
}
