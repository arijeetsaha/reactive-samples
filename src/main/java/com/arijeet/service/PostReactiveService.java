package com.arijeet.service;

import com.arijeet.dbmock.CommentsService;
import com.arijeet.dbmock.PostInfoService;
import com.arijeet.domain.Post;
import com.arijeet.exception.PostException;
import com.arijeet.exception.NetworkException;
import com.arijeet.exception.ServiceException;
import lombok.AllArgsConstructor;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@AllArgsConstructor
public class PostReactiveService {

    private PostInfoService postInfoService;
    private CommentsService commentsService;


    /**
     * Sample method which shows a publisher(Mono/Flux) dependant on another publisher.
     * commentFlux is executed using the output of postInfoFlux.
     * @return
     */
    public Flux<Post> getAllPosts() {
        var postInfoFlux =  postInfoService.retrievePostsFlux()
                .log();
        return postInfoFlux
                .log()
                .flatMap(postInfo -> {
                    var commentFlux = commentsService.retrieveCommentsFlux(postInfo.getPostInfoId());
                    return commentFlux
                            .collectList()
                            .map(comments -> new Post(postInfo, comments));
                })
                .onErrorMap((ex) -> {
                    return new PostException(ex);
                })
                .log();
    }

    public Flux<Post> getAllPostsWithRetry() {
        var postInfoFlux =  postInfoService.retrievePostsFlux()
                .log();
        return postInfoFlux
                .log()
                .flatMap(postInfo -> {
                    var reviewsFlux = commentsService.retrieveCommentsFlux(postInfo.getPostInfoId());
                    return reviewsFlux
                            .collectList()
                            .map(reviews -> new Post(postInfo, reviews));
                })
                .onErrorMap((ex) -> {
                    return new PostException(ex);
                })
                .retry(3)
                .log();
    }


    /**
     * retryWhen
     *      - network exception happens
      */

    public Flux<Post> getAllPostsWithRetryWhen() {
        var postInfoFlux =  postInfoService.retrievePostsFlux()
                .log();
        var retryWhen = getRetryBackoffSpec();

        return postInfoFlux
                .log()
                .flatMap(postInfo -> {
                    var commentFlux = commentsService.retrieveCommentsFlux(postInfo.getPostInfoId());
                    return commentFlux
                            .collectList()
                            .map(comments -> new Post(postInfo, comments));
                })
                .onErrorMap((ex) -> {
                    if(ex instanceof NetworkException) {
                        return new PostException(ex);
                    }
                    return new ServiceException(ex.getMessage());
                })
                .retryWhen(retryWhen)
                .log();
    }

    /**
     * Retry Spec definition
     */
    private RetryBackoffSpec getRetryBackoffSpec() {
        return Retry.backoff(3, Duration.ofMillis(500))
                .filter(ex -> ex instanceof PostException)
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> {
                    return Exceptions.propagate(retrySignal.failure());
                }));
    }

    /**
     * repeat
     *      - used to repeat an existing sequence
     *      - invoked after the onCompletion() event from existing sequence
     */
    public Flux<Post> getAllPostsWithRetryWhenRepeat() {
        var postInfoFlux =  postInfoService.retrievePostsFlux()
                .log();
        var retryWhen = getRetryBackoffSpec();

        return postInfoFlux
                .log()
                .flatMap(postInfo -> {
                    var commentFlux = commentsService.retrieveCommentsFlux(postInfo.getPostInfoId());
                    return commentFlux
                            .collectList()
                            .map(comments -> new Post(postInfo, comments));
                })
                .onErrorMap((ex) -> {
                    if(ex instanceof NetworkException) {
                        return new PostException(ex);
                    }
                    return new ServiceException(ex.getMessage());
                })
                .retryWhen(retryWhen)
                .repeat()
                .log();
    }


    /**
     *
     * Merging multiple publisher output to one.
     * Output of postInfoMono and commentFlux are merged to one.
     */
    public Mono<Post> getPost(Long postInfoId) {
        var postInfoMono = postInfoService.retrievePostInfoMonoUsingId(postInfoId);
        var commentFlux = commentsService.retrieveCommentsFlux(postInfoId);
        return postInfoMono.zipWith(commentFlux.collectList())
                .map(T2 -> new Post(T2.getT1(), T2.getT2()));
    }


}
