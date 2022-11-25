package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono(){
        Mono mono = Mono.just(1)
                .then(Mono.error(new RuntimeException("exception ocurred")))
                .log();
        mono.subscribe(System.out::println, (throwable) -> {
            System.out.println(throwable);
        });
    }

    @Test
    public void testFlux(){
        Flux flux = Flux.just("hola", "como", "esta")
                .concatWith(Flux.error(new RuntimeException("exception ocurred")))
                .concatWithValues("AWS").log();
        flux.subscribe(System.out::println,(throwable) -> {
            System.out.println(throwable);
        });
    }
}
