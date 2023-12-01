package com.example.lab7.models;

import com.example.lab7.pks.ProductPricePK;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product_price")
@IdClass(ProductPricePK.class)
public class ProductPrice {
    @Id
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @Id
    @Column(name = "price_date_time")
    private LocalDateTime priceDateTime;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "note")
    private String note;
}
