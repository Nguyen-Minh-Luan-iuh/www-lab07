package com.example.lab7.repositories;

import com.example.lab7.enums.ProductStatus;
import com.example.lab7.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByStatusEquals(ProductStatus status, Pageable pageable);
}
