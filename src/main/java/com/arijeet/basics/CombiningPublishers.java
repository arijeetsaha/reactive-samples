package com.arijeet.basics;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CombiningPublishers {

    //concat/concatWith - combining 2 reactive streams to one
    //                  - concat happens in sequence
    //                      - first one is subscribed first and completes
    //                      - second is subscribed after that and then completes
    // concat           - static method
    // concatWith       - instance method in flux and mono

    public Flux<String> concatFluxes() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        return Flux.concat(abc, def).log();
    }

    public Flux<String> concatWithFluxes() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        return abc.concatWith(def).log();
    }

    public Flux<String> concatWithMonos() {
        var abc = Mono.just("A");
        var def = Mono.just("D");
        return abc.concatWith(def).log();
    }

    //merge - merge in interleaved fashion
    //      - both publishers are subscribed at the same time
    //      - publishers are subscribed eagerly and merge happens in interleaved fashion
    // merge           - static method
    // mergeWith       - instance method in flux and mono

    public Flux<String> mergeFluxes() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        return Flux.merge(abc, def).log();
    }

    public Flux<String> mergeWithFluxes() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        return abc.mergeWith(def).log();
    }

    public Flux<String> mergeWithMonos() {
        var abc = Mono.just("A");
        var def = Mono.just("D");
        return abc.mergeWith(def).log();
    }

    //mergeSequential

    //Zip - static function of flux
    //    -  merge 2-8 publishers
    //    - publishers are subscribed eagerly
    //    - wait for all the publishers involved to emit one element
    //    - continues until one publisher sends an oncomplete event

    public Flux<String> zipFluxes() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        return Flux.zip(abc, def, (a,b) -> a+b).log();
    }

    public Flux<String> zipFluxesMultiple() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        var _123Flux = Flux.just("1", "2", "3");
        return Flux.zip(abc, def, _123Flux)
                .map(T3 -> T3.getT1()+T3.getT2()+T3.getT3())
                .log();
    }
}
