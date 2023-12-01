package com.example.lab7.controllers;

import com.example.lab7.models.Customer;
import com.example.lab7.models.Product;
import com.example.lab7.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class BaseController {
    @Autowired
    private ProductService productService;
    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin/index";
    }

    @GetMapping("")
    public String getHomePage(HttpSession session, Model model, @Param("page")Optional<Integer> page, @Param("limit")Optional<Integer> limit) {
        int currentPage = page.orElse(1);
        int currentLimit = limit.orElse(10);

        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null) {
            customer = new Customer();
        }

        Page<Product> products = productService.findPublishedProduct(currentPage, currentLimit);
        int totalPage = products.getTotalPages();
        String[] pages = new String[totalPage];
        Arrays.fill(pages, "");

        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if(cart == null){
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("totalCart", cart.size());
        model.addAttribute("customer", customer);
        model.addAttribute("totalPage", pages);
        model.addAttribute("products", products.getContent());
        return "index";
    }
}
