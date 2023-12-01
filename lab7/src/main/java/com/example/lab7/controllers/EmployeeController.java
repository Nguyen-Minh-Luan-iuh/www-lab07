package com.example.lab7.controllers;

import com.example.lab7.enums.EmployeeStatus;
import com.example.lab7.models.Employee;
import com.example.lab7.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String employeeList(@Param("page") Optional<Integer> page, @Param("limit")Optional<Integer>limit, Model model){
        int finalPage = page.orElse(1);
        int finalLimit = limit.orElse(10);

        Page<Employee> result = employeeService.findAll(finalPage, finalLimit);
        List<Employee> employees = result.getContent();
        String[] totalPages = new String[result.getTotalPages()];
        Arrays.fill(totalPages, "");

        model.addAttribute("employees", employees);
        model.addAttribute("totalPages", totalPages);
        return "admin/employee/list";
    }

    @GetMapping("/new")
    public String addEmployee(Model model){
        Employee employee = new Employee();
        EmployeeStatus[] employeeStatuses = EmployeeStatus.values();

        model.addAttribute("employee", employee);
        model.addAttribute("employeeStatuses", employeeStatuses);
        return "admin/employee/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee")Employee employee){
        employeeService.save(employee);

        return "redirect:/admin/employees";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        EmployeeStatus[] employeeStatuses = EmployeeStatus.values();
        Optional<Employee> result = employeeService.findById(id);
        if(result.isEmpty()) return "redirect:admin/employees";

        model.addAttribute("employee", result.get());
        model.addAttribute("employeeStatuses", employeeStatuses);
        return "admin/employee/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id){
        employeeService.remove(id);

        return "redirect:/admin/employees";
    }
}
