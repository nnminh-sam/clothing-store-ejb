package com.ptithcm.ejb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "carts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    private double totalPrice;

    @ManyToOne
    @JoinColumn (name = "productVariant_id")
    private ProductVariant productVariant;
}
