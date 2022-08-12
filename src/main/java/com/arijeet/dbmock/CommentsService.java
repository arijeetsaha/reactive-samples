package com.arijeet.dbmock;

import com.arijeet.domain.Comment;
import reactor.core.publisher.Flux;

import java.util.List;

public class CommentsService {

    public  List<Comment> retrieveComments(long postInfoId){

        return List.of(new Comment(1L, postInfoId, "Awesome Movie", 8.9),
                new Comment(2L, postInfoId, "Excellent Movie", 9.0));
    }

    public Flux<Comment> retrieveCommentsFlux(long postInfoId){

        var commentList = List.of(new Comment(1L,postInfoId, "Awesome Movie", 8.9),
                new Comment(2L, postInfoId, "Excellent Movie", 9.0));
        return Flux.fromIterable(commentList);
    }

}
