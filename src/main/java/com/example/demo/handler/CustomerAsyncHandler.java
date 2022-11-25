package com.example.demo.handler;


import com.example.demo.dao.CustomerDao;
import com.example.demo.dto.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerAsyncHandler {

    private final CustomerDao customerDao;

    public CustomerAsyncHandler(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request){
        Flux<Customer> customersAsync = this.customerDao.getCustomersAsync();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersAsync, Customer.class);
    }
}
