package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.domain.Range;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByPriceBetween(Range<Double> range);
}
