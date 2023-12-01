package com.example.lab7.services;

import com.example.lab7.models.Customer;
import com.example.lab7.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void insert(Customer customer) {
        customerRepository.save((customer));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> login(String email, String password) {
        return customerRepository.findCustomerByEmailAndPassword(email, password);
    }

    public  Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

}
