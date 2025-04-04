package com.ptithcm.ejb.service.remote;

import com.ptithcm.ejb.entity.Cart;
import com.ptithcm.ejb.entity.Order;
import com.ptithcm.ejb.entity.OrderDetail;
import com.ptithcm.ejb.entity.User;
import com.ptithcm.ejb.enums.OrderStatusEnum;
import com.ptithcm.ejb.enums.PaymentMethodEnum;
import com.ptithcm.ejb.enums.PaymentStatusEnum;

import com.ptithcm.ejb.service.OrderServiceRemote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderService implements OrderServiceRemote {
    @PersistenceContext
    private EntityManager entityManager;

    public Order createOrder(List<Integer> cartIds, String userId, String address, String phone) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(PaymentMethodEnum.COD);
        order.setAddress(address);
        order.setPhone(phone);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.PENDING);
        order.setPaymentStatus(PaymentStatusEnum.PENDING);

        List<OrderDetail> orderDetails = new ArrayList<>();
        double totalPrice = 0;
        for (int cartId : cartIds) {
            Cart cart = entityManager.find(Cart.class, cartId);
            if (cart == null) {
                throw new RuntimeException("CART_NOT_FOUND");
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .price(cart.getTotalPrice() / cart.getQuantity())
                    .productVariant(cart.getProductVariant())
                    .quantity(cart.getQuantity())
                    .order(order)
                    .build();

            orderDetails.add(orderDetail);

            totalPrice += cart.getTotalPrice();

            entityManager.remove(cart);
        }

        order.setOrderDetails(orderDetails);
        order.setTotal_price(totalPrice);
        entityManager.persist(order);

        return order;
    }
}

