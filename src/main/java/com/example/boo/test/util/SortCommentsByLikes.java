package com.example.boo.test.util;

import com.example.boo.test.collections.Comment;

import java.util.Comparator;

public class SortCommentsByLikes implements Comparator<Comment> {

    // Sorting in descending order of Likes
    @Override
    public int compare(Comment comment1, Comment comment2) {

        if (comment1.getLikes() == comment2.getLikes())
            return 0;
        else if (comment1.getLikes() < comment2.getLikes())
            return 1;
        else
            return -1;
    }
}