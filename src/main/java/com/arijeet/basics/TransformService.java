package com.arijeet.basics;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

/**
 * transform - transform from one type to  another
 *           - accepts Function functional interface
 *           - Input is Publisher
 *           - Output is Publisher
 */
public class TransformService {

    public Flux<String> transform (int size) {

        //Behavior parameterization
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(n -> n.length() >size);

        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
                .transform(filterMap)
                .flatMap(n -> splitString(n))
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> transformSwitchIfEmpty (int size) {

        //Behavior parameterization
        Function<Flux<String>, Flux<String>> filterMap = name -> name
                .filter(n -> n.length() >size)
                .map(String::toUpperCase)
                .flatMap(n -> splitString(n));

        var switchFlux = Flux.just("default1111")
                .transform(filterMap);

        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
                .transform(filterMap)
                .switchIfEmpty(switchFlux)
                .log();
    }

    private Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

}
