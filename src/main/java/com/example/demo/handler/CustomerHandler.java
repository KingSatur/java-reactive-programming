package com.example.demo.handler;

import com.example.demo.dao.CustomerDao;
import com.example.demo.dto.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    private final CustomerDao dao;

    public CustomerHandler(CustomerDao dao) {
        this.dao = dao;
    }

    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest){
        Flux<Customer> customersAsync = dao.getCustomersNoDelay();
        return ServerResponse.ok().body(customersAsync, Customer.class);
    }

    public Mono<ServerResponse> loadCustomer(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Customer> customerMono = dao.getCustomersNoDelay().filter(c -> c.getIndex() == input).single();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest){
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> map = customerMono.map(dto -> dto.getIndex() + " : " + dto.getName());
        return ServerResponse.ok().body(map, String.class);
    }
}
