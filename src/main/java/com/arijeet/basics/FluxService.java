package com.arijeet.basics;

import com.arijeet.exception.ReactorException;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static com.arijeet.util.CommonUtil.delay;

public class FluxService {

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
    public Flux<String> nameFlux() {
        var namesFlux = Flux.fromIterable(List.of("arijeet", "binoy", "sudip"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    //flatMap - transforms one source element to a Flux of 1 to N elements
    //        - Use this when the transformation returns a reactive type(flux or mono)
    //        - used to return Publisher (always returns Flux<T>/Mono<T>)
    //        - use for 1 to N transformation
    //        - subscribes to flux/mono that's part of transformation and then flattens and sends it to downstream
    //        - used for async transformations
    //        - use this if transformation involves making REST API call or a functionality which can be done asynchronously.

    public Flux<String> flatMapExample() {
        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
                .filter(name -> name.length() >4)
                .map(String::toUpperCase)
                .flatMap(n -> splitString(n))
                .log();
    }

    public Flux<String> flatMapAsyncExample() {
        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
                .filter(name -> name.length() >4)
                .map(String::toUpperCase)
                .flatMap(n -> splitStringWithDelay(n))
                .log();
    }

    //concatMap - same as flatMap with sync operation
    //          - async operation + preserve ordering
    //          - overall timing
    public Flux<String> concatMapExample() {
        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
                .filter(name -> name.length() >4)
                .map(String::toUpperCase)
                .concatMap(n -> splitString(n))
                .log();
    }

    /**
     * doOn callbacks
     *      - allows to peek in to the event emitted by publisher
     *      - side effect operators
     *      - donot change the original sequence at all
     *          - doOnSubscribe
     *          - doOnNext
     *          - doOnComplete
     *          - doOnError
     *          - doFinally
     *      - Use when debugging in local env
     *      - send notification when reative sequence completes or error out
     */

    public Flux<String> doOnExample() {
        return Flux.fromIterable(List.of("arijeet", "bini", "sudip"))
                .filter(name -> name.length() >4)
                .map(String::toUpperCase)
                .doOnNext(str -> {
                    System.out.println("Name is: " + str);
                    str.toLowerCase();
                })
                .doOnSubscribe(s -> {
                    System.out.println("Subscription is : "+s);
                })
                .doOnComplete(() -> {
                    System.out.println("Reactive stream data completely consumed");
                })
                .doFinally(signalType -> {
                    System.out.println("inside finally : "+signalType);
                })
                .log();
    }

    public Flux<String> exceptionFlux() {
        return Flux.just("A","B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Flux.just("D"))
                .log();
    }

    /**
     * Exception handling Operators -
     *      - Recover from exception
     *          - onErrorReturn
     *          - onErrorResume
     *          - onErrorContinue
     *      - Take an action and throw exception
     *          - onErrorMap
     *          - doOnError
      */

    /**
     * onErrorReturn
     *      - catch exception
     *      - provides single default value as fallback value
     */
    public Flux<String> onErrorReturnExample() {
        return Flux.just("A","B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .onErrorReturn("D")
                .log();
    }

    /**
     * onErrorResume
     * @param e
     * @return
     */
    public Flux<String> onErrorResumeExample(Exception e) {

        var recoveryFlux = Flux.just("D", "E", "F");

        return Flux.just("A","B", "C")
                .concatWith(Flux.error(e))
                .onErrorResume(ex ->{
                    if(ex instanceof IllegalStateException)
                        return recoveryFlux;
                    else
                        return Flux.error(ex);
                })
                .log();
    }


    /**
     * onErrorContinue -
     *      Drops the element that caused the exception and continue emitting the remaining elements

     */
    public Flux<String> onErrorContinueExample(Exception e) {

         return Flux.just("A","B", "C")
                .concatWith(Flux.just("D", "E", "F"))
                .map(name -> {
                    if(name.equals("B")) {
                        throw new IllegalStateException();
                    }
                    return name;
                })
                .onErrorContinue((ex, name) -> {
                    System.out.println("Exception is : "+ ex);
                    System.out.println("Name is : "+ name);
                })
                .log();
    }

    /**
     * onErrorMap
     *      - catches the exception
     *      - transforms exception from one type to another
     *          - runtime to business exception
     *      - does not recover from exception
     *      - remaining elements will be terminated from executing
     *
     */
    public Flux<String> onErrorMapExample(Exception e) {

        return Flux.just("A","B", "C")
                .concatWith(Flux.just("D", "E", "F"))
                .map(name -> {
                    if(name.equals("B")) {
                        throw new IllegalStateException();
                    }
                    return name;
                })
                .onErrorMap((ex) -> {
                    System.out.println("Exception is : "+ ex);
                    return new ReactorException(ex, ex.getMessage());
                })
                .log();
    }

    /**
     * doOnError
     *      - catches the exception
     *      - takes action when the exception occurs
     *      - donot modify the reactive stream by any means
     *      - error still gets propagated to the caller
     *
     */

    public Flux<String> doOnErrorExample(Exception e) {

        return Flux.just("A","B", "C")
                .concatWith(Flux.just("D", "E", "F"))
                .map(name -> {
                    if(name.equals("B")) {
                        throw new IllegalStateException();
                    }
                    return name;
                })
                .doOnError((ex) -> {
                    System.out.println("Exception is : "+ ex);
                })
                .log();
    }


    private Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    private Flux<String> splitStringWithDelay(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }
}
