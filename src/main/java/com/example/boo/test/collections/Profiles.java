package com.example.boo.test.collections;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "profiles")
public class Profiles {

    @Transient
    public static final String SEQUENCE_NAME = "profile_sequence";

    @Id
    private String id;
    private Long profileSeq;
    private String name;
    private String description;
    private String mbti;
    private String enneagram;
    private String variant;
    private Integer tritype;
    private String socionics;
    private String sloan;
    private String psyche;
    private String image;
    private List<Comment> comments;
}