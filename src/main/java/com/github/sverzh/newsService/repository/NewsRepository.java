package com.github.sverzh.newsService.repository;

import com.github.sverzh.newsService.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findAll(Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.title LIKE %:title% or n.text LIKE %:text%")
    List<News> filter(@Param("title") String title, @Param("text") String text);
}

