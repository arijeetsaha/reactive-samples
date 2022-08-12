package com.arijeet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfo {
    private Long postInfoId;
    private String name;
    private Integer year;
    private List<String> friends;
    private LocalDate postDate;
}