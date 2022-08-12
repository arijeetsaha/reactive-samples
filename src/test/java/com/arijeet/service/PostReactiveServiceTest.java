package com.arijeet.service;

import com.arijeet.dbmock.CommentsService;
import com.arijeet.dbmock.PostInfoService;
import com.arijeet.exception.PostException;
import com.arijeet.exception.NetworkException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostReactiveServiceTest {
    @InjectMocks
    PostReactiveService postReactiveServiceMock;

    @Mock
    PostInfoService postInfoServiceMock;

    @Mock
    CommentsService commentsServiceMock;

    @Test
    void getAllMovies() {

        Mockito.when(postInfoServiceMock.retrievePostsFlux())
                        .thenCallRealMethod();

        Mockito.when(commentsServiceMock.retrieveCommentsFlux(anyLong()))
                        .thenCallRealMethod();

        var postFlux = postReactiveServiceMock.getAllPosts();

        StepVerifier.create(postFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getAllMoviesException() {
        Mockito.when(postInfoServiceMock.retrievePostsFlux())
                .thenCallRealMethod();

        Mockito.when(commentsServiceMock.retrieveCommentsFlux(anyLong()))
                .thenThrow(new RuntimeException());

        var postFlux = postReactiveServiceMock.getAllPosts();

        StepVerifier.create(postFlux)
                .expectError(PostException.class)
                .verify();
    }

    @Test
    void getAllMoviesExceptionWithRetry() {
        Mockito.when(postInfoServiceMock.retrievePostsFlux())
                .thenCallRealMethod();

        Mockito.when(commentsServiceMock.retrieveCommentsFlux(anyLong()))
                .thenThrow(new RuntimeException());

        var postFlux = postReactiveServiceMock.getAllPostsWithRetry();

        StepVerifier.create(postFlux)
                .expectError(PostException.class)
                .verify();

        verify(commentsServiceMock, times(4)).retrieveCommentsFlux(isA(Long.class));
    }

    @Test
    void getAllMoviesExceptionWithRetryWhen() {
        Mockito.when(postInfoServiceMock.retrievePostsFlux())
                .thenCallRealMethod();

        Mockito.when(commentsServiceMock.retrieveCommentsFlux(anyLong()))
                .thenThrow(new NetworkException("Network exception"));

        var postFlux = postReactiveServiceMock.getAllPostsWithRetryWhen();

        StepVerifier.create(postFlux)
                .expectError(PostException.class)
                .verify();

        verify(commentsServiceMock, times(4)).retrieveCommentsFlux(isA(Long.class));
    }

    @Test
    void getAllMoviesExceptionWithoutRetryWhen() {
        Mockito.when(postInfoServiceMock.retrievePostsFlux())
                .thenCallRealMethod();

        Mockito.when(commentsServiceMock.retrieveCommentsFlux(anyLong()))
                .thenThrow(new RuntimeException("Network exception"));

        var postFlux = postReactiveServiceMock.getAllPostsWithRetryWhen();

        StepVerifier.create(postFlux)
                .expectError(PostException.class)
                .verify();

        verify(commentsServiceMock, times(4)).retrieveCommentsFlux(isA(Long.class));
    }

    @Test
    void getAllMoviesRepeat() {
        Mockito.when(postInfoServiceMock.retrievePostsFlux())
                .thenCallRealMethod();

        Mockito.when(commentsServiceMock.retrieveCommentsFlux(anyLong()))
                .thenCallRealMethod();

        var posts = postReactiveServiceMock.getAllPostsWithRetryWhenRepeat();

        StepVerifier.create(posts)
                .expectNextCount(6)
                .thenCancel()
                .verify();

        verify(commentsServiceMock, times(6)).retrieveCommentsFlux(isA(Long.class));
    }

}