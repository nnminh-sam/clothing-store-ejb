package com.ptithcm.ejb.service.remote;

import com.ptithcm.ejb.entity.Product;
import com.ptithcm.ejb.entity.ProductVariant;
import com.ptithcm.ejb.service.ProductVariantServiceRemote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

@Stateless
public class ProductVariantService implements ProductVariantServiceRemote {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductVariant findByColorBySize(String color, String size) throws Exception {
        ProductVariant productVariant = this.entityManager
                .createQuery(
                        "SELECT v FROM ProductVariant v WHERE v.color = :color AND V.size = :size",
                        ProductVariant.class)
                .setParameter("color", color)
                .setParameter("size", size)
                .getSingleResult();
        if (productVariant == null) {
            throw new Exception("Product variant not found!");
        }
        return productVariant;
    }

    @Override
    @Transactional
    public List<ProductVariant> updateProductVariant(Map<Integer, ProductVariant> productVariantRequests) {
        List<ProductVariant> list = productVariantRequests.entrySet()
                .stream()
                .map(entry -> {
                    int variantId = entry.getKey();
                    ProductVariant request = entry.getValue();

                    ProductVariant productVariant = entityManager.find(ProductVariant.class, variantId);
                    if (productVariant == null) {
                        throw new RuntimeException("Product variant not found");
                    }
                    return productVariant;
                })
                .toList();

        list.forEach(entityManager::merge);
        entityManager.flush();

        return list;
    }


    @Override
    @Transactional
    public String deleteProductVariant(List<Integer> ids) {
        List<ProductVariant> variants = entityManager
                .createQuery("SELECT v FROM ProductVariant v WHERE v.id IN :ids", ProductVariant.class)
                .setParameter("ids", ids)
                .getResultList();

        if (variants.isEmpty()) {
            throw new RuntimeException("Product variant not found");
        }

        variants.forEach(entityManager::remove);
        entityManager.flush();

        return "List product variant deleted";
    }
}

