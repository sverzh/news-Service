package com.github.sverzh.newsService.service;

import com.github.sverzh.newsService.exception.CustomEmptyDataException;
import com.github.sverzh.newsService.model.News;
import com.github.sverzh.newsService.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public News getNewsById(Long newsId) {
        Optional<News> newsGet = newsRepository.findById(newsId);
        if (newsGet.isPresent()) {
            return newsGet.get();
        } else {
            throw new CustomEmptyDataException("unable to load news with id: " + newsId);
        }
    }

    @Transactional
    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Transactional
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Transactional
    public News updateNews(News source, Long newsId) {
        Optional<News> newsForUpdate = newsRepository.findById(newsId);
        if (newsForUpdate.isPresent()) {
            News target = newsForUpdate.get();
            target.setText(source.getText());
            target.setTitle(source.getTitle());
            target.setDate(source.getDate());
            newsRepository.save(target);
            return target;
        } else {
            throw new CustomEmptyDataException("unable to update news");
        }
    }

    @Transactional
    public String deleteNews(Long newsId) {
        Optional<News> newsForDelete = newsRepository.findById(newsId);
        if (newsForDelete.isPresent()) {
            newsRepository.delete(newsForDelete.get());
            return "News with id: " + newsId + " deleted";
        } else {
            throw new CustomEmptyDataException("unable to delete news");
        }
    }

}

