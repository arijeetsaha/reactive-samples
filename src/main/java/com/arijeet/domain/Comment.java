package com.arijeet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long reviewId;
    private Long movieInfoId;
    private String comment;
    private Double rating;
}