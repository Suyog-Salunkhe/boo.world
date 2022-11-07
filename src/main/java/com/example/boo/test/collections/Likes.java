package com.example.boo.test.collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "likes")
public class Likes {

    @Id
    private String id;

    private Long profileSeq;

    private List<Long> commentSeq;

    public Likes(Long profileSeq, List<Long> commentSeq) {
        this.profileSeq = profileSeq;
        this.commentSeq = commentSeq;
    }
}
