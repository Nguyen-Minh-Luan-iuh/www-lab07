package com.example.lab7;

import com.example.lab7.repositories.EmployeeRepository;
import com.example.lab7.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab7Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab7Application.class, args);
    }

}
