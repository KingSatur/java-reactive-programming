package com.example.demo.service;


import com.example.demo.dao.CustomerDao;
import com.example.demo.dto.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> loadAllCustomers(){
        long start = System.currentTimeMillis();
        List<Customer> customers = customerDao.getCustomers();
        long finalTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (finalTime - start));
        return customers;
    }
    public Flux<Customer> loadAllCustomersAsync(){
        long start = System.currentTimeMillis();
        Flux<Customer> customers = customerDao.getCustomersAsync();
        long finalTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (finalTime - start));
        return customers;
    }

}
