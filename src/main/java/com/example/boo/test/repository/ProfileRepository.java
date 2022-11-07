package com.example.boo.test.repository;

import com.example.boo.test.collections.Profiles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profiles, String> , CrudRepository<Profiles, String> {

    Optional<Profiles> findByProfileSeq(Long profileSeq );

    @Query(value= "{profileSeq: ?0}", fields="{comments:1}")
    Profiles findCommentsByProfileSeq(Long profileSeq);
}