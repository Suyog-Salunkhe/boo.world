package com.example.boo.test.repository;

import com.example.boo.test.collections.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends MongoRepository<Likes, String>, CrudRepository<Likes, String> {

    Likes findByProfileSeq(Long profileSeq);
}
