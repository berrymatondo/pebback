package com.peb.pebb.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    // Get all comments

    public List<Comments> getComments() {
        return commentsRepository.findAll();
    }

    // Add comments
    public Comments addComments(Comments comments) {
        return commentsRepository.save(comments);
    }

}
