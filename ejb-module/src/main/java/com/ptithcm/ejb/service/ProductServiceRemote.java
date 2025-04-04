package com.ptithcm.ejb.service;

import com.ptithcm.ejb.entity.Product;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProductServiceRemote {

    Product findById(int id) throws Exception;

    List<Product> findAll();

    Product create(Product product);

    Product update(Product product) throws Exception;

    String deleteProduct(int id) throws Exception;
}
