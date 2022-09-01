package com.arijeet.dbmock;

import com.arijeet.domain.PostInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static com.arijeet.util.CommonUtil.delay;

public class PostInfoService {

    public  Flux<PostInfo> retrievePostsFlux(List<PostInfo> postInfos){

        var postInfos1 = List.of(new PostInfo(100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new PostInfo(101L,"The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new PostInfo(102L,"Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        return Flux.fromIterable(postInfos1);
    }

    public  Flux<PostInfo> retrievePostsFlux(){

        var postInfos = List.of(new PostInfo(100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new PostInfo(101L,"The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new PostInfo(102L,"Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        return Flux.fromIterable(postInfos);
    }

    public  Mono<PostInfo> retrievePostInfoMonoUsingId(long postId){

        var postInfo = new PostInfo(postId, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        return Mono.just(postInfo);
    }

    public  List<PostInfo> postList(){
        delay(1000);

        return List.of(new PostInfo(100L, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new PostInfo(101L,"The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new PostInfo(102L,"Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));
    }

    public PostInfo retrievePostUsingId(long postId){
        delay(1000);
        return new PostInfo(postId, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));
    }

}