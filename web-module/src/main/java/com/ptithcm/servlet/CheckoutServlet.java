package com.ptithcm.servlet;

import com.ptithcm.ejb.service.OrderServiceRemote;
import com.ptithcm.ejb.entity.User;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @EJB
    private OrderServiceRemote orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to cart page if accessed directly
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // assuming user is stored in session

        if (user == null) {
            session.setAttribute("alert", "true");
            session.setAttribute("alertMessage", "Bạn cần đăng nhập trước khi thanh toán.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String[] cartIdParams = request.getParameterValues("cartIds");
        if (cartIdParams == null || cartIdParams.length == 0) {
            session.setAttribute("alert", "true");
            session.setAttribute("alertMessage", "Vui lòng chọn ít nhất một sản phẩm để thanh toán.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");

            List<Integer> cartIds = Arrays.stream(cartIdParams)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            orderService.createOrder(cartIds, user.getId(), address, phone);

            // Clear cart from session
            session.setAttribute("cart", null);

            // Set success alert
            session.setAttribute("alert", "true");
            session.setAttribute("alertMessage", "Đặt hàng thành công!");

            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (Exception e) {
            session.setAttribute("alert", "true");
            session.setAttribute("alertMessage", "Lỗi khi đặt hàng: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}