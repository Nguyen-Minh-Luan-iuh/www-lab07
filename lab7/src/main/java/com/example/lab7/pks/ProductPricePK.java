package com.example.lab7.pks;


import com.example.lab7.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.io.Serializable;

@Getter
@Setter
public class ProductPricePK implements  Serializable{
    private Product product;
    private LocalDateTime priceDateTime;
}
