package com.example.lab7.helpers;

import com.example.lab7.models.Product;
import com.example.lab7.models.ProductPrice;

import java.time.LocalDateTime;

public class ProductHelper {
    public static void addProductPrice(String[] priceDateTimes, String[] prices, Product product){
        for(int i = 0; i <priceDateTimes.length; i++){
            ProductPrice productPrice = new ProductPrice(product, LocalDateTime.parse(priceDateTimes[i]), Double.parseDouble(prices[i]), "");
            product.addProductPrice(productPrice);
        }
    }
}
