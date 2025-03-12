package com.example.blog_with_jwt;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<Article> getElements() {
        return repository.findAll();
    }

    public void create(Article object) {
        repository.save(object);
    }
}
