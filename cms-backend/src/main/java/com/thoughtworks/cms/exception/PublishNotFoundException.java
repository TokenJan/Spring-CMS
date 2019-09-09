package com.thoughtworks.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublishNotFoundException extends RuntimeException{
    public PublishNotFoundException(Long contentId) {
        super(String.format("Cannot find published version of content [%d]", contentId));
    }
}
