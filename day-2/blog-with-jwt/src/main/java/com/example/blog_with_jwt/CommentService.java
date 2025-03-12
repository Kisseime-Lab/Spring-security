package com.example.blog_with_jwt;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> getElements() {
        return repository.findAll();
    }

    public void create(Comment object) {
        repository.save(object);
    }
}