package com.example.demo.router;

import com.example.demo.handler.CustomerAsyncHandler;
import com.example.demo.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {


    private final CustomerHandler customerHandler;
    private final CustomerAsyncHandler customerAsyncHandler;

    public RouterConfig(CustomerHandler customerHandler, CustomerAsyncHandler customerAsyncHandler) {
        this.customerHandler = customerHandler;
        this.customerAsyncHandler = customerAsyncHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/customer", customerHandler::loadCustomers)
                .GET("/router/customer/async", customerAsyncHandler::getCustomers)
                .GET("/router/customer/{input}", customerHandler::loadCustomer)
                .POST("/router/customer", customerHandler::saveCustomer)
                .build();
    }

}
