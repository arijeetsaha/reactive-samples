package com.arijeet.basics;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.arijeet.util.CommonUtil.delay;

public class MonoService {

    static List<String> namesList = List.of("alex", "ben", "chloe");
    static List<String> namesList1 = List.of("adam", "jill", "jack");

    private String upperCase(String name) {
        delay(1000);
        return name.toUpperCase();
    }

    //map : transform the element from one form to another in a reactive stream
    //     - one to one transformation
    //     - simple synchronous transformations
    //     - does not support transformations that return publisher
    //Reactive stream is immutable
    public Mono<String> nameMono() {
        var namesMono = Mono.just("arijeet");
        namesMono.map(String::toUpperCase);
        return namesMono;
    }

    //flatMap - transforms one source element to a Flux of 1 to N elements
    //        - Use this when the transformation returns a reactive type(flux or mono)
    //        - used to return Publisher (always returns Flux<T>/Mono<T>)
    //        - use for 1 to N transformation
    //        - subscribes to flux/mono that's part of transformation and then flattens and sends it to downstream
    //        - used for async transformations
    //        - use this if transformation involves making REST API call or a functionality which can be done asynchronously.

    public Mono<List<String>> flatMapExample() {
        return Mono.just("arijeet")
                .filter(name -> name.length() >4)
                .map(String::toUpperCase)
                .flatMap(n -> splitString(n))
                .log();
    }

    //flatMapMany - Similar to flatMap
    //            - returns Flux from Mono
    public Flux<String> flatMapManyExample() {
        return Mono.just("arijeet")
                .filter(name -> name.length() >4)
                .map(String::toUpperCase)
                .flatMapMany(n -> splitStringToFlux(n))
                .log();
    }

//    public Mono<String> flatMapAsyncExample() {
//        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
//                .filter(name -> name.length() >4)
//                .map(String::toUpperCase)
//                .flatMap(n -> splitStringWithDelay(n))
//                .log();
//    }
//
//    //concatMap - same as flatMap with sync operation
//    //          - async operation + preserve ordering
//    //          - overall timing
//    public Flux<String> concatMapExample() {
//        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
//                .filter(name -> name.length() >4)
//                .map(String::toUpperCase)
//                .concatMap(n -> splitString(n))
//                .log();
//    }

    private Mono<List<String>> splitString(String name) {
        var charArray = name.split("");
        return Mono.just(List.of(charArray));
    }

    private Flux<String> splitStringToFlux(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }
//    private Flux<String> splitStringWithDelay(String name) {
//        var charArray = name.split("");
//        return Flux.fromArray(charArray)
//                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
//    }
}
