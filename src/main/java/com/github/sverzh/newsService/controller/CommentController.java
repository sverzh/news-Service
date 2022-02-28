package com.github.sverzh.newsService.controller;

import com.github.sverzh.newsService.model.Comment;
import com.github.sverzh.newsService.service.CommentService;
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
@RequestMapping(value = "/rest/news/")
public class CommentController {
    private final CommentService commentService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{newsId}/comments")
    public ResponseEntity<Page<Comment>> getAllComments(@PathVariable Long newsId, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        Page<Comment> result = commentService.getAllCommentsByNewsId(newsId, pageable);
        log.info("getAllComments for News with id={}", newsId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{newsId}/comments/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId, @PathVariable Long newsId) {
        Comment result = commentService.getComment(commentId, newsId);
        log.info("getComment with id={} for News with id={}", commentId, newsId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{newsId}/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Long newsId) {
        Comment result = commentService.createComment(comment, newsId);
        log.info("create Comment in News with id={}: {}", newsId, comment);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{newsId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @PathVariable Long newsId, @RequestBody Comment source) {
        Comment result = commentService.updateComment(source, commentId, newsId);
        log.info("update Comment with id={} in News with id={}", commentId, newsId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{newsId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @PathVariable Long newsId) {
        log.info("delete Comment with id={} in News with id={}", commentId, newsId);
        return new ResponseEntity<>(commentService.deleteComment(commentId, newsId), HttpStatus.OK);
    }

    @GetMapping("/{newsId}/comments/filter")
    public List<Comment> filter(@PathVariable Long newsId,
                                @RequestParam @Nullable String text,
                                @RequestParam @Nullable String username
    ) {
        log.info("Searching Comment with text={} and username={}", text, username);
        return commentService.commentFilter(newsId, text, username);
    }
}

