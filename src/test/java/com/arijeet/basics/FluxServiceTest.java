package com.arijeet.basics;

import com.arijeet.exception.ReactorException;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxServiceTest {

    FluxService fluxService = new FluxService();

    @Test
    void nameFlux() {
        var flux = fluxService.nameFlux();

        StepVerifier.create(flux)
                .expectNext("ARIJEET", "BINOY", "SUDIP")
                .verifyComplete();
    }


    @Test
    void flatMapAsyncExample() {
        var flux = fluxService.flatMapAsyncExample();
        StepVerifier.create(flux)
                //.expectNext("A","R","I","J","E","E","T", "S","U","D","I","P")
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void concatMapExample() {
        var flux = fluxService.concatMapExample();
        StepVerifier.create(flux)
                .expectNext("A","R","I","J","E","E","T", "S","U","D","I","P")
                //.expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void doOnExample() {
        var flux = fluxService.doOnExample();

        StepVerifier.create(flux)
                .expectNext("ARIJEET", "SUDIP")
                .verifyComplete();
    }

    @Test
    void exceptionFlux() {
        var value = fluxService.exceptionFlux();

        StepVerifier.create(value)
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void exceptionFlux1() {
        var value = fluxService.exceptionFlux();

        StepVerifier.create(value)
                .expectNext("A", "B", "C")
                .expectError()
                .verify();
    }

    @Test
    void exceptionFlux2() {
        var value = fluxService.exceptionFlux();

        StepVerifier.create(value)
                .expectNext("A", "B", "C")
                .expectErrorMessage("Exception occurred")
                .verify();

    }

    @Test
    void onErrorReturnExample() {
        var value = fluxService.onErrorReturnExample();

        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D")
                .verifyComplete();
    }

    @Test
    void onErrorResumeExample() {
        var value = fluxService.onErrorResumeExample(new IllegalStateException());

        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void onErrorContinueExample() {
        var value = fluxService.onErrorContinueExample(new IllegalStateException());

        StepVerifier.create(value)
                .expectNext("A", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void onErrorMapExample() {

        var value = fluxService.onErrorMapExample(new IllegalStateException());

        StepVerifier.create(value)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }


    @Test
    void doOnErrorExample() {

        var value = fluxService.doOnErrorExample(new IllegalStateException());

        StepVerifier.create(value)
                .expectNext("A")
                .expectError(IllegalStateException.class)
                .verify();
    }
}