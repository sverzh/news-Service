package com.github.sverzh.newsService.controller;

import com.github.sverzh.newsService.model.News;
import com.github.sverzh.newsService.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/")
public class NewsController {
    private final NewsService newsService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ResponseEntity<Page<News>> getAllNews(@PageableDefault(page = 0, size = 4) Pageable pageable) {
        Page<News> result = newsService.getAllNews(pageable);
        log.info("getAllNews");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/news")
    public ResponseEntity<News> createUser(@RequestBody News news) {
        News result = newsService.createNews(news);
        log.info("create News: {}", news);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<News> getNews(@PathVariable Long id) {
        News result = newsService.getNewsById(id);
        log.info("get News with id={}", id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News source) {
        News result = newsService.updateNews(source, id);
        log.info("update News with id={}", id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        log.info("delete News with id={}", id);
        return new ResponseEntity<>(newsService.deleteNews(id), HttpStatus.OK);
    }

    @GetMapping("/news/filter")
    public List<News> filter(
            @RequestParam @Nullable String title,
            @RequestParam @Nullable String text
    ) {
        log.info("Searching News with title={} and text={}", title, text);
        return newsService.newsFilter(title, text);
    }
}
