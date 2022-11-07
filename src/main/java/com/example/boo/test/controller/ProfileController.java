package com.example.boo.test.controller;

import com.example.boo.test.collections.Comment;
import com.example.boo.test.collections.Profiles;
import com.example.boo.test.requests.CreateProfileRequest;
import com.example.boo.test.requests.PostCommentRequest;
import com.example.boo.test.service.ProfileService;
import com.example.boo.test.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiles")
    public ResponseEntity<?> getAllProfiles() {
        List<Profiles> list = profileService.getAllProfiles();
        if(!list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(Constants.NO_PROFILES_FOUND, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile/{profileSeq}")
    public ResponseEntity<?> getProfile(@PathVariable("profileSeq") Long profileSeq) {
        Profiles profile = profileService.getProfileByProfileSeq(profileSeq);
        if(null != profile){
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        return new ResponseEntity<>(Constants.NO_PROFILES_FOUND, HttpStatus.NO_CONTENT);
    }

    //create user accounts,
    @PostMapping("/createprofile")
    public ResponseEntity<?> addProfile(@RequestBody CreateProfileRequest profileRequest) {

        if(profileService.createProfile(profileRequest)){
            return new ResponseEntity<>(Constants.PROFILE_CREATED, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(Constants.PROFILE_CREATION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // post comment
    @PostMapping("/postcomment")
    public ResponseEntity<String> postComment(@RequestBody @NotNull PostCommentRequest postCommentRequest) {
        int status = profileService.postComment(postCommentRequest);
        if(Constants.OK_VALUE == status){
            return new ResponseEntity<>(Constants.POSTED_SUCCESSSFULLY, HttpStatus.OK);
        }
        if(Constants.UNPROCESSABLE_ENTITY_VALUE == status){
            return new ResponseEntity<>(Constants.NO_PROFILES_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Constants.POST_CREATION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // get comments
    @GetMapping("/getcomments/{profilesSeq}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("profilesSeq") Long profilesSeq) {

        List<Comment> comments = profileService.getAllComments(profilesSeq);
        if(!comments.isEmpty()){
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    // sort comments
    @GetMapping("/sortcomments/{profileSeq}/{sortBy}")
    public ResponseEntity<List<Comment>> sortComments(@PathVariable("profileSeq") Long profileSeq,
                                                      @PathVariable("sortBy") String sortBy) {

        List<Comment> comments = profileService.sortComments(profileSeq, sortBy);
        if(comments.isEmpty()){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // filter comments
    @GetMapping("/filter/{profileSeq}/{personality}")
    public ResponseEntity<List<Comment>> filterCommentsByPersonality(@PathVariable("profileSeq") Long profileSeq,
                                                                     @PathVariable("personality") String personality) {
        List<Comment> comments = profileService.filterComments(profileSeq, personality);
        if(comments.isEmpty()){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    // like and unlike comment
    @GetMapping("/likeOrUnlike/{id}/{commentSeq}/{isLiked}")
    public ResponseEntity<?> likeOrUnlike(@PathVariable("id") Long profileSeq,
                                          @PathVariable Long commentSeq, @PathVariable Boolean isLiked) {
        Integer response = profileService.likeOrUnlikeComment(profileSeq, commentSeq, isLiked);
        if(Constants.OK_VALUE == response){
            return new ResponseEntity<>(Constants.LIKE_UNLIKE_SUCCEED, HttpStatus.OK);
        }
        if(Constants.UNPROCESSABLE_ENTITY_VALUE == response) {
            return new ResponseEntity<>(Constants.NO_PROFILES_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(Constants.POST_LIKE_UNLIKE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
