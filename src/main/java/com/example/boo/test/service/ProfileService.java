package com.example.boo.test.service;

import com.example.boo.test.collections.Comment;
import com.example.boo.test.collections.Likes;
import com.example.boo.test.collections.Profiles;
import com.example.boo.test.repository.LikesRepository;
import com.example.boo.test.repository.ProfileRepository;
import com.example.boo.test.requests.CreateProfileRequest;
import com.example.boo.test.requests.PostCommentRequest;
import com.example.boo.test.util.Constants;
import com.example.boo.test.util.SortCommentsByDate;
import com.example.boo.test.util.SortCommentsByLikes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    public List<Profiles> getAllProfiles(){
        List<Profiles> list = profileRepository.findAll();
        return list;
    }

    public Profiles getProfileByProfileSeq(Long profileSeq){
        Optional<Profiles> optionalProfiles = profileRepository.findByProfileSeq(profileSeq);
        if(optionalProfiles.isPresent()){
            return optionalProfiles.get();
        }
        return null;
    }

    public boolean createProfile(CreateProfileRequest profileRequest) {
        try {
            Profiles profiles = Profiles.builder()
                    .profileSeq(sequenceGenerator.generateSequence(Profiles.SEQUENCE_NAME))
                    .name(profileRequest.getName())
                    .description(profileRequest.getDescription())
                    .mbti(profileRequest.getMbti())
                    .enneagram(profileRequest.getEnneagram())
                    .variant(profileRequest.getVariant())
                    .tritype(profileRequest.getTritype())
                    .socionics(profileRequest.getSocionics())
                    .sloan(profileRequest.getSloan())
                    .psyche(profileRequest.getPsyche())
                    .image(profileRequest.getImage())
                    .build();
            profileRepository.save(profiles);
            return true;
        }catch (Exception e){
            log.error(Constants.PROFILE_CREATION_FAILED, e.getMessage());
        }
        return false;
    }

    public Integer postComment(PostCommentRequest postComment){
        Optional<Profiles> optionalProfile = profileRepository.findByProfileSeq(postComment.getProfileSeq());
        Profiles profile = null;
        if(optionalProfile.isPresent()) {
            profile = optionalProfile.get();
            List<Comment> comments = profile.getComments();
            if (comments == null || comments.isEmpty()) {
                comments = new ArrayList<>();
            }
            comments.add(new Comment(sequenceGenerator.generateSequence(Comment.COMMENT_SEQUENCE), postComment.getComment(),postComment.getPersonalityType() ));
            profile.setComments(comments);
            try {
                profileRepository.save(profile);
            }catch (Exception ex){
                log.error(Constants.POST_CREATION_FAILED, ex.getMessage());
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }
            return HttpStatus.OK.value();
        }
        return HttpStatus.UNPROCESSABLE_ENTITY.value();
    }

    public List<Comment> getAllComments(Long profilesSeq){
        Profiles profiles = profileRepository.findCommentsByProfileSeq(profilesSeq);
        if(null == profiles){
            return Collections.emptyList();
        }
        return profiles.getComments();
    }

    public List<Comment> sortComments(Long profileSeq, String sortBy){
        Profiles profiles = profileRepository.findCommentsByProfileSeq(profileSeq);
        if(null == profiles){
            return Collections.emptyList();
        }
        List<Comment> comments = profiles.getComments();
        if(sortBy.equalsIgnoreCase(Constants.LIKES)){
            Collections.sort(comments, new SortCommentsByLikes());
        } else if(sortBy.equals(Constants.MOST_RECENT)) {
            Collections.sort(comments, new SortCommentsByDate());
        }
        return comments;
    }

    public List<Comment> filterComments(Long profileSeq, String filterBy){
        Profiles profiles = profileRepository.findCommentsByProfileSeq(profileSeq);
        if(null == profiles){
            return Collections.emptyList();
        }
        List<Comment> comments = profiles.getComments();

        comments = comments.stream()
                .filter(comment -> comment.getPersonalityType()
                        .equalsIgnoreCase(filterBy))
                .collect(Collectors.toList());

        return comments;
    }

    public Integer likeOrUnlikeComment(Long profileSeq, Long commentSeq, Boolean isLiked) {
        Optional<Profiles> optionalProfile = profileRepository.findByProfileSeq(profileSeq);
        Profiles profile = null;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
        } else {
            return HttpStatus.UNPROCESSABLE_ENTITY.value();
        }
        List<Comment> comments = profile.getComments();
        try {
            comments.stream()
                    .filter(c -> c.getCommentSeq() == commentSeq)
                    .forEach(c -> {
                        if (isLiked) {
                            c.setLikes(c.getLikes() + 1);
                        } else {
                            c.setLikes(c.getLikes() - 1);
                        }
                    });
            profile.setComments(comments);
            profileRepository.save(profile);

            Likes likes = likesRepository.findByProfileSeq(profile.getProfileSeq());
            List<Long> commentSeqeunces = null;
            if (likes != null) {
                commentSeqeunces = likes.getCommentSeq();
            }
            if (isLiked) {
                if (commentSeqeunces == null || commentSeqeunces.isEmpty()) {
                    commentSeqeunces = new ArrayList<>();
                    commentSeqeunces.add(commentSeq);
                    if (likes == null) {
                        likes = new Likes(profileSeq, commentSeqeunces);
                    }
                } else {
                    if (!commentSeqeunces.contains(commentSeq)) {
                        commentSeqeunces.add(commentSeq);
                    }
                }
            } else {
                commentSeqeunces.remove(commentSeq);
            }
            likes.setCommentSeq(commentSeqeunces);
            likesRepository.save(likes);
            return HttpStatus.OK.value();
        } catch (Exception ex){
            log.error(Constants.PROFILE_CREATION_FAILED , Constants.COLON, ex.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }
}
