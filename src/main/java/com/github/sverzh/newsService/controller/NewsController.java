package com.github.sverzh.newsService.controller;

import com.github.sverzh.newsService.model.News;
import com.github.sverzh.newsService.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ResponseEntity<Page<News>> getAllNews(@PageableDefault(page = 0, size = 4) Pageable pageable) {
        Page<News> result = newsService.getAllNews(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/news")
    public ResponseEntity<News> createUser(@RequestBody News news) {
        News result = newsService.createNews(news);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<News> getNews(@PathVariable Long id) {
        News result = newsService.getNewsById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News source){
        News result = newsService.updateNews(source,id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id){
        return new ResponseEntity<>(newsService.deleteNews(id), HttpStatus.OK);
    }
}
