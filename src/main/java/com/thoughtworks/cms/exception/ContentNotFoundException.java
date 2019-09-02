package com.thoughtworks.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(Long contentId) {
        super(String.format("Cannot find content [%d]", contentId));
    }
}
