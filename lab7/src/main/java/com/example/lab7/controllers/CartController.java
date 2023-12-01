package com.example.lab7.controllers;

import com.example.lab7.helpers.OrderHelper;
import com.example.lab7.models.Customer;
import com.example.lab7.models.Order;
import com.example.lab7.models.Product;
import com.example.lab7.services.OrderService;
import com.example.lab7.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/cart")
    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, HttpSession session, Model model){
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null){
            return "redirect:/login";
        }

        Optional<Product> result = productService.findById(productId);
        if(result.isEmpty()) return "redirect:/";

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if(cart == null){
            cart = new HashMap<>();
        }

        if(cart.containsKey(result.get())){
            cart.put(result.get(), cart.get(result.get()) + quantity);
        }
        else{
            cart.put(result.get(), quantity);
        }

        session.setAttribute("cart", cart);
        return "redirect:/";
    }
    @GetMapping("/cart")
    public String getCart(Model model, HttpSession session){
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null){
            return "redirect:/";
        }

        if(cart == null){
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("cart", cart);

        return "customer/checkout/cart";
    }

    @PostMapping("/increase")
    public String increaseQuantity(@RequestParam("productId")Long productId, Model model, HttpSession session){
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        cart.forEach((product, quantity) -> {
            if(product.getProduct_id() == productId){
                cart.put(product, quantity + 1);
            }
        });
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/decrease")
    public String decreaseQuantity(@RequestParam("productId")Long productId, Model model, HttpSession session){
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        List<Product> itemsToRemove = new ArrayList<>();
        cart.forEach((product, quantity) -> {
            if(product.getProduct_id() == productId){
                if(quantity == 1){
                    itemsToRemove.add(product);
                }
                else {
                    cart.put(product, quantity - 1);
                }
            }
        });

        itemsToRemove.forEach(cart::remove);
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeProduct(@RequestParam("productId")Long productId, Model model, HttpSession session){
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        List<Product> itemsToRemove = new ArrayList<>();
        cart.forEach((product, quantity) -> {
            if(product.getProduct_id() == productId){
                itemsToRemove.add(product);
            }
        });

        itemsToRemove.forEach(cart::remove);
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session, Model model){
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        cart.clear();
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model){
        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        Customer customer = (Customer) session.getAttribute("customer");
        Order order = new Order();
        OrderHelper.addOrderDetail(cart, customer, order);
        orderService.save(order);

        session.removeAttribute("cart");

        return "redirect:/";
    }
}
