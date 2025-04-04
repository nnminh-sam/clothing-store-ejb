package com.ptithcm.servlet;

import com.ptithcm.ejb.entity.Cart;
import com.ptithcm.ejb.entity.ProductVariant;
import com.ptithcm.ejb.entity.User;
import com.ptithcm.ejb.service.CartServiceRemote;
import com.ptithcm.ejb.service.ProductVariantServiceRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @EJB
    private CartServiceRemote cartService;

    @EJB
    private ProductVariantServiceRemote productVariantService;

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        response.setContentType("application/json");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("requireLogin", "true");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            List<Cart> cart = cartService.getCartByUserId(user.getId());
            request.setAttribute("cart", cart);

            request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        response.setContentType("application/json");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("requireLogin", "true");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String action = request.getParameter("action");

        if ("remove".equals(action)) {
            try {
                int cartId = Integer.parseInt(request.getParameter("cartId"));
                cartService.deleteCart(cartId);
                request.getSession().setAttribute("alert", "true");
                request.getSession().setAttribute("alertMessage", "Cart removed successfully");
                response.sendRedirect(request.getContextPath() + "/cart");
            } catch (NumberFormatException exception) {
                request.getSession().setAttribute("error", "Invalid cart ID");
                response.sendRedirect(request.getContextPath() + "/cart");
            } catch (Exception exception) {
                request.getSession().setAttribute("error", "Cannot remove item out of cart");
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        } else {
            String productIdParam = request.getParameter("productId");
            String quantityParam = request.getParameter("quantity");
            String colorParam = request.getParameter("color");
            String sizeParam = request.getParameter("size");

            try {
                ProductVariant productVariant = this.productVariantService.findByColorBySize(colorParam, sizeParam);
                System.out.println(">>>> ID   : " + productVariant.getId());
                System.out.println(">>>> Color: " + productVariant.getColor());
                System.out.println(">>>> Size : " + productVariant.getSize());
                System.out.println(">>>> Quant: " + quantityParam);
//            TODO: Add try catch for creating cart
                Cart cart = this.cartService.createCart(user.getId(), Integer.parseInt(quantityParam), productVariant.getId());
                System.out.println(">>>> Cart ID: " + cart.getId());
                System.out.println(">>>> Var ID : " + cart.getProductVariant().getId());
                request.getSession().setAttribute("alert", "true");
                request.getSession().setAttribute("alertMessage", "Product added to cart successfully");
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productIdParam);
            } catch (Exception e) {
                request.getSession().setAttribute(
                        "error",
                        "Cannot find product variant with color " + colorParam + " and size " + sizeParam);
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productIdParam);
            }
        }
    }
}
