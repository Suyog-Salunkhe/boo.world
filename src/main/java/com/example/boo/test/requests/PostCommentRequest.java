package com.example.boo.test.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRequest {

    private Long profileSeq;
    private String personalityType;
    private String comment;

}
