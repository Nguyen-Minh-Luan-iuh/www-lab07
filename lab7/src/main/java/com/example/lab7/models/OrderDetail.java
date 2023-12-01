package com.example.lab7.models;

import com.example.lab7.pks.OrderDetailPK;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "order_detail")
@IdClass(OrderDetailPK.class)
public class OrderDetail {
    @Id
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private double quantity;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "note")
    private String note;


    public OrderDetail(Integer quantity, double price, String s, Order order, Product product) {
    }
}
