package com.example.blog_with_jwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Provider.Service;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BlogController {

    private final ArticleService aService;
    private final CommentService cService;

    public BlogController(ArticleService aService, CommentService cService) {
        this.aService = aService;
        this.cService = cService;
    }

    @PostMapping("/articles")
    public String creer(@RequestBody Article article) {
        aService.create(article);
        return "Article crée";
    }

    @GetMapping("/articles")
    public List<Article> getMethodName(@RequestParam String auteur) {
        return aService.getElements();
    }

    @PostMapping("/comments")
    public String comment(@RequestBody Comment entity) {
        cService.create(entity);
        return "Comment cree";
    }

    @GetMapping("/comment/{article}")
    public List<Comment> commentairesArticle(@PathVariable String articleId) {
        return cService.getElements().stream().filter(c -> c.getArticleId() == Integer.parseInt(articleId)).toList();
    }

}
