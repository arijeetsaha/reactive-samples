package com.arijeet.basics;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class TransformServiceTest {

    TransformService transformService = new TransformService();

    @Test
    void transform() {
        var flux = transformService.transform(4);
        StepVerifier.create(flux)
                .expectNext("A","R","I","J","E","E","T", "S","U","D","I","P")
                .verifyComplete();
    }

    @Test
    void transform_empty() {
        var flux = transformService.transform(10);
        StepVerifier.create(flux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void transform_switchifempty() {
        var flux = transformService.transformSwitchIfEmpty(10);
        StepVerifier.create(flux)
                .expectNext("D", "E", "F", "A", "U", "L", "T", "1", "1", "1", "1")
                .verifyComplete();

    }
}