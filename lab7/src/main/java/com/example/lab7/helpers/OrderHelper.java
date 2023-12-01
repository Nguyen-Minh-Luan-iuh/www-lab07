package com.example.lab7.helpers;

import com.example.lab7.models.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

public class OrderHelper {
    public static void addOrderDetail(Map<Product, Integer> cart, Customer customer, Order order){
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);
        order.setEmployee(new Employee(new Random().nextInt(50)));
        cart.forEach((product, quantity) -> {
            double price = 0;
            if(product.getCurrentPrice().isPresent()) price = product.getCurrentPrice().get().getPrice();

            OrderDetail orderDetail = new OrderDetail(quantity, price, "", order, product);
            order.addOrderDetail(orderDetail);
        });
    }
}
