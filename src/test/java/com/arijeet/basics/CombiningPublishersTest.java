package com.arijeet.basics;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class CombiningPublishersTest {

    CombiningPublishers combiningPublishers = new CombiningPublishers();

    @Test
    void concatFluxes() {
        var combinedFLUX = combiningPublishers.concatFluxes();

        StepVerifier.create(combinedFLUX)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatWithFluxes() {
        var combinedFLUX = combiningPublishers.concatWithFluxes();

        StepVerifier.create(combinedFLUX)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatWithMonos() {
        var combinedFLUX = combiningPublishers.concatWithMonos();

        StepVerifier.create(combinedFLUX)
                .expectNext("A", "D")
                .verifyComplete();
    }

    @Test
    void mergeFluxes() {
        var combinedFLUX = combiningPublishers.mergeFluxes();

        StepVerifier.create(combinedFLUX)
                .expectNextCount(6 )
                //.expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void zipFluxes() {
        var combinedFLUX = combiningPublishers.zipFluxes();

        StepVerifier.create(combinedFLUX)
//                .expectNextCount(3 )
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void zipMultipleFluxes() {
        var combinedFLUX = combiningPublishers.zipFluxesMultiple();

        StepVerifier.create(combinedFLUX)
                .expectNext("AD1", "BE2", "CF3`")
                .verifyComplete();
    }
}