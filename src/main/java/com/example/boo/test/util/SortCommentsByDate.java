package com.example.boo.test.util;

import com.example.boo.test.collections.Comment;

import java.util.Comparator;

public class SortCommentsByDate implements Comparator<Comment> {


    @Override
    public int compare(Comment comment1, Comment comment2) {
        // reverse order
        return comment2.getCreatedDate().compareTo(comment1.getCreatedDate());
    }
}
