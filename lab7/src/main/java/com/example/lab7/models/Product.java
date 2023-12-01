package com.example.lab7.models;

import com.example.lab7.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product")
@NamedQueries(value = {
        @NamedQuery(name = "Product.findAll", query = "select p from Product p where p.status = :status"),
        @NamedQuery(name = "Product.findById", query = "select p from Product p where p.product_id = ?1")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long product_id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @Lob
    @Column(name = "description",  columnDefinition = "text", nullable = false)
    private String description;
    @Column(name = "unit", length = 25, nullable = false)
    private String unit;
    @Column(name = "manufacturer_name", length = 100, nullable = false)
    private String manufacturer;

    @Column(name = "status")
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductPrice> productPrices = new ArrayList<>();

    public Product(String name, String description, String unit, String manufacturer, ProductStatus status) {
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.manufacturer = manufacturer;
        this.status = status;
    }

    public Optional<ProductPrice> getCurrentPrice(){
        LocalDateTime now = LocalDateTime.now();
        return this.productPrices.stream().filter(el -> el.getPriceDateTime().isBefore(now)).max(Comparator.comparing(ProductPrice::getPriceDateTime));
    }

    public void addProductPrice(ProductPrice price) {
        this.productPrices.add(price);
    }
}
