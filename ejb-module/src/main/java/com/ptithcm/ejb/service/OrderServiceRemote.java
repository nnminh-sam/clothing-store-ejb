package com.ptithcm.ejb.service;

import com.ptithcm.ejb.entity.Order;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface OrderServiceRemote {
    public Order createOrder(List<Integer> cartIds, String userId, String address, String phone);
}
