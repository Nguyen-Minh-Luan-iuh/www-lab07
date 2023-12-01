package com.example.lab7.controllers;

import com.example.lab7.models.Customer;
import com.example.lab7.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession session){
        Customer auth = (Customer) session.getAttribute("customer");
        if(auth != null){
            return "redirect:/";
        }

        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/auth/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model, HttpSession session){
        Customer auth = (Customer) session.getAttribute("customer");
        if(auth != null){
            return "redirect:/";
        }

        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/auth/register";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("customer") Customer customer, Model model, HttpSession session){
        Optional<Customer> result = customerService.login(customer.getEmail(), customer.getPassword());
        if(result.isEmpty()){
            model.addAttribute("errorMessage", "Bad credentials");
            return "customer/auth/login";
        }

        session.setAttribute("customer", result.get());
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("customer") Customer customer, Model model, HttpSession session){
        Optional<Customer> result = customerService.findCustomerByEmail(customer.getEmail());
        if(result.isPresent()){
            model.addAttribute("errorMessage", "This email is already in used");
            return "customer/auth/register";
        }
        customerService.insert(customer);

        session.setAttribute("customer", customer);
        return "redirect:/";
    }

}
