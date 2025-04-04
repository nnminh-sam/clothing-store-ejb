package com.ptithcm.servlet;

import com.ptithcm.ejb.entity.Product;
import com.ptithcm.ejb.entity.ProductVariant;
import com.ptithcm.ejb.entity.User;
import com.ptithcm.ejb.service.ProductServiceRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {

    @EJB
    private ProductServiceRemote productService;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("id");
        if (id.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/home");
        }

        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            Product product = productService.findById(productId);

            request.setAttribute("product", product);

            List<ProductVariant> variants = product.getProductVariantList();

            List<String> colors = variants.stream()
                    .map(ProductVariant::getColor)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> sizes = variants.stream()
                    .map(ProductVariant::getSize)
                    .distinct()
                    .collect(Collectors.toList());

            request.setAttribute("colors", colors);
            request.setAttribute("sizes", sizes);
            request.setAttribute("variants", variants);

            request.getRequestDispatcher("/WEB-INF/product-detail.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Cannot retrieve product: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/product-detail.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(">>> Debug User ID: " + user.getId());
    }
}
