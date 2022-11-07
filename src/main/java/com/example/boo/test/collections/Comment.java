package com.example.boo.test.collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment implements Comparable<Comment> {

    @Transient
    public static final String COMMENT_SEQUENCE = "comment_sequence";

    private Long commentSeq;
    private String comment;
    private Integer likes;
    private String personalityType;
    private Date createdDate;

    public Comment(Long sequence, String comment, String personalityType){
        this.commentSeq = sequence;
        this.comment = comment;
        this.personalityType = personalityType;
        this.likes = 0;
        this.createdDate = new Date();
    }

    @Override
    public int compareTo(Comment comment) {
        if (getCreatedDate() == null || comment.getCreatedDate() == null)
            return 0;
        return getCreatedDate().compareTo(comment.getCreatedDate());
    }
}