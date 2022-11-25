package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Flux<ProductDto> getProducts(){
        return this.productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable("id") String id){
        return this.productService.getProduct(id);
    }

    @GetMapping("/range")
    public Flux<ProductDto> getProductsInRange(@RequestParam("min") double min, @RequestParam("max") double max){
        return this.productService.findProductInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return this.productService.saveProduct(productDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<ProductDto> saveProduct(@PathVariable("id") String id, @RequestBody Mono<ProductDto> productDtoMono){
        return this.productService.updateProduct(id,productDtoMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") String id){
        return this.productService.deleteProduct(id);
    }

}
