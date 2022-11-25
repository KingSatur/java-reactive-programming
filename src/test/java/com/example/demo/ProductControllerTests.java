package com.example.demo;

import com.example.demo.controller.ProductController;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;


    @Test
    public void addProductTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102","Iphone", 1, 14234));
        Mockito.when(productService.saveProduct(Mockito.any())).thenReturn(
                productDtoMono
        );

        webTestClient.post().uri("/product").body(productDtoMono, ProductDto.class)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void getProducts(){
        Mockito.when(productService.getProducts()).thenReturn(
                Flux.just(new ProductDto("102","Iphone", 12, 14234),
                        new ProductDto("103","Xiomi", 543, 14234),
                        new ProductDto("104","Samsung", 213, 14234)
                ,new ProductDto("152","4090 Nvidia", 67, 14234))
        );

        Flux<ProductDto> productDtoFlux = webTestClient.get().uri("/product")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(productDtoFlux)
                .expectSubscription()
                .expectNext(new ProductDto("102","Iphone", 12, 14234))
                .expectNext( new ProductDto("103","Xiomi", 543, 14234))
                .expectNext(new ProductDto("104","Samsung", 213, 14234))
                .expectNext(new ProductDto("152","4090 Nvidia", 67, 14234))
                .verifyComplete();

    }

    @Test
    public void getProductTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102","Iphone", 12, 14234));
        Mockito.when(productService.getProduct(Mockito.any())).thenReturn(productDtoMono);
        Flux<ProductDto> productDtoResponseFlux = webTestClient.get().uri("/product/102")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();


        StepVerifier.create(productDtoResponseFlux)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equals("Iphone"))
                .verifyComplete();

    }

    @Test
    public void updateProductTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102","Iphone", 12, 14234));
        Mono<ProductDto> productUpdatedDtoMono = Mono.just(new ProductDto("102","IphonePlar0", 400, 10123));
        Mockito.when(productService.updateProduct(Mockito.any(),Mockito.any())).thenReturn(productUpdatedDtoMono);
        Flux<ProductDto> productDtoResponseFlux = webTestClient.put().uri("/product/102")
                .body(productDtoMono, ProductDto.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();


        StepVerifier.create(productDtoResponseFlux)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equals("IphonePlar0"))
                .verifyComplete();
    }

    @Test
    public void deleteProductTest(){
        given(productService.deleteProduct(Mockito.any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/product/102")
                .exchange()
                .expectStatus().isOk();
    }

}
