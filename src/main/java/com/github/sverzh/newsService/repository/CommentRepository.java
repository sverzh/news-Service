package com.github.sverzh.newsService.repository;

import com.github.sverzh.newsService.model.Comment;
import com.github.sverzh.newsService.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByNews(News news);

    @Query("SELECT c FROM Comment c join fetch c.news where c.id=?1 and c.news.id=?2")
    Comment getWithNewsId(@Param("id") Long id, @Param("news_id")Long newsId);

}