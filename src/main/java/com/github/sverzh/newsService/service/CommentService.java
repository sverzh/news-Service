package com.github.sverzh.newsService.service;

import com.github.sverzh.newsService.exception.CustomEmptyDataException;
import com.github.sverzh.newsService.logging.Loggable;
import com.github.sverzh.newsService.model.Comment;
import com.github.sverzh.newsService.model.News;
import com.github.sverzh.newsService.repository.CommentRepository;
import com.github.sverzh.newsService.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Loggable
    public Page<Comment> getAllCommentsByNewsId(Long newsId, Pageable pageable) {
        Optional<News> newsFindOptional = newsRepository.findById(newsId);
        if (newsFindOptional.isPresent()) {
            return commentRepository.findAllByNews(newsFindOptional.get(), pageable);
        } else {
            throw new NoSuchElementException("No news with id:" + newsId + " was found");
        }
    }

    @Transactional
    @Loggable
    public Comment createComment(Comment comment, Long newsId) {
        Optional<News> commentNewsOptional = newsRepository.findById(newsId);
        if (commentNewsOptional.isPresent()) {
            comment.setNews(commentNewsOptional.get());
            commentRepository.save(comment);
            return comment;
        } else {
            throw new CustomEmptyDataException("unable to get News for comment");
        }
    }

    @Transactional
    @Loggable
    public Comment getComment(Long commentId, Long newsId) {
        return commentRepository.getWithNewsId(commentId, newsId);
    }

    @Transactional
    @Loggable
    public Comment updateComment(Comment source, Long commentId, Long newsId) {
        Optional<Comment> commentForUpdate = commentRepository.findById(commentId);
        Optional<News> newsFindOptional = newsRepository.findById(newsId);
        if (commentForUpdate.isPresent() && newsFindOptional.isPresent()) {
            Comment target = commentForUpdate.get();
            target.setText(source.getText());
            target.setDate(source.getDate());
            target.setUsername(source.getUsername());
            commentRepository.save(target);
            return target;
        } else {
            throw new NoSuchElementException("unable to update comment");
        }
    }

    @Transactional
    @Loggable
    public String deleteComment(Long commentId, Long newsId) {
        Comment commentForDelete = commentRepository.getWithNewsId(commentId, newsId);
        if (commentForDelete != null) {
            commentRepository.delete(commentForDelete);
            return "Comment with id: " + commentId + " deleted";
        } else {
            throw new NoSuchElementException("unable to delete comment (id=" + commentId + ") in News (id=" + newsId + ")");
        }
    }

    @Loggable
    public List<Comment> commentFilter(Long newsId, @Nullable String text, @Nullable String username) {
        return commentRepository.filter(newsId, text, username);
    }
}