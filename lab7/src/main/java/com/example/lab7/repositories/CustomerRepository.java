package com.example.lab7.repositories;

import com.example.lab7.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findCustomerByEmailAndPassword(String email, String password);

    public Optional<Customer> findCustomerByEmail(String email);
}
