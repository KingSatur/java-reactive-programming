package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto){
        return productDto.map(
                productDto1 -> new Product(
                            null,
                                productDto1.getName(),
                                productDto1.getQuantity(),
                                productDto1.getPrice()
                )
        ).flatMap(
                product ->    this.productRepository.insert(product)
        ).map(
                productEntity -> ProductDto.builder()
                        .id(productEntity.getId())
                        .price(productEntity.getPrice())
                        .name(productEntity.getName())
                        .quantity(productEntity.getQuantity()).build()
        );
    }

    public Flux<ProductDto> getProducts(){
        return this.productRepository.findAll().map(productEntity ->ProductDto.builder()
                .id(productEntity.getId())
                .price(productEntity.getPrice())
                .name(productEntity.getName())
                .quantity(productEntity.getQuantity()).build());
    }

    public Mono<ProductDto> getProduct(String id){
        return this.productRepository.findById(id).map(productEntity ->ProductDto.builder()
                .id(productEntity.getId())
                .price(productEntity.getPrice())
                .name(productEntity.getName())
                .quantity(productEntity.getQuantity()).build());
    }

    public Flux<ProductDto> findProductInRange(double min, double max){
        return this.productRepository.findByPriceBetween(Range.closed(min, max)).map(
                productEntity -> ProductDto.builder()
                        .id(productEntity.getId())
                        .price(productEntity.getPrice())
                        .name(productEntity.getName())
                        .quantity(productEntity.getQuantity()).build());
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
        return this.productRepository.findById(id)
                .flatMap(
                    productEntity -> productDtoMono.map(productDto ->
                                    new Product(
                                            null,
                                            productDto.getName(),
                                            productDto.getQuantity(),
                                            productDto.getPrice()
                                    )
                            )
                )
                .doOnNext(e -> e.setId(id))
                .flatMap(productRepository::save)
                .map(entity -> ProductDto.builder()
                                .id(entity.getId())
                                .price(entity.getPrice())
                                .name(entity.getName())
                                .quantity(entity.getQuantity()).build()
                );

    }

    public Mono<Void> deleteProduct(String id){
        return productRepository.deleteById(id);
    }

}
