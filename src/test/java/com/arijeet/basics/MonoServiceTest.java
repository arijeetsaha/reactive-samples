package com.arijeet.basics;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class MonoServiceTest {

    MonoService monoService = new MonoService();
    @Test
    void flatMapExample() {
        Mono<List<String>> listMono = monoService.flatMapExample();
        StepVerifier.create(listMono)
                .expectNext(List.of("A", "R", "I", "J", "E", "E", "T"))
                .verifyComplete();
    }

    @Test
    void flatMapManyExample() {
        Flux<String> stringFlux = monoService.flatMapManyExample();
        StepVerifier.create(stringFlux)
                .expectNext("A", "R", "I", "J", "E", "E", "T")
                .verifyComplete();
    }
}