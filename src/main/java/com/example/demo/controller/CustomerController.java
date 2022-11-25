package com.example.demo.controller;


import com.example.demo.dto.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController

@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return this.customerService.loadAllCustomers();
    }

    @GetMapping(value = "/async", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getCustomersAsync(){
        System.out.println("aqui mor");
        return this.customerService.loadAllCustomersAsync();
    }

}
