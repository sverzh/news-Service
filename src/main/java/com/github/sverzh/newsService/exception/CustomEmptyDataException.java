package com.github.sverzh.newsService.exception;

import org.springframework.dao.DataAccessException;


public class CustomEmptyDataException extends DataAccessException {
    public CustomEmptyDataException(String msg) {
        super(msg);
    }
}
