package com.example.lab7.services;

import com.example.lab7.models.Order;
import com.example.lab7.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void save(Order order){
        orderRepository.save(order);
    }

    public Page<Order> findAll(int page, int limit){
        return orderRepository.findAll(PageRequest.of(page - 1, limit).withSort(Sort.by("orderDate").descending()));
    }

    public Optional<Order> findById(long id){
        return orderRepository.findById(id);
    }

    public Page<Order> findAll(LocalDateTime startDate, LocalDateTime endDate, long customerId, long employeeId, int page, int limit){
        if(employeeId == 0 && customerId == 0){
            return orderRepository.findOrdersByOrderDateBetween(startDate, endDate, PageRequest.of(page - 1, limit).withSort(Sort.by("orderDate").descending()));
        }

        if(customerId == 0){
            return orderRepository.findOrdersByEmployeeIdAndOrderDateBetween(employeeId, startDate, endDate, PageRequest.of(page - 1, limit).withSort(Sort.by("orderDate").descending()));
        }

        if(employeeId == 0){
            return orderRepository.findOrdersByCustomerIdAndOrderDateBetween(customerId, startDate, endDate, PageRequest.of(page - 1, limit).withSort(Sort.by("orderDate").descending()));
        }

        return orderRepository.findOrdersByCustomerIdAndEmployeeIdAndOrderDateBetween(customerId, employeeId, startDate, endDate, PageRequest.of(page - 1, limit).withSort(Sort.by("orderDate").descending()));
    }
}
