package com.ptithcm.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "product_variants")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private int quantity;

    @Min(value = 0, message = "Product must be greater than or equal to 0")
    private double price;

    private String image;

    private String color;

    private String size;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
