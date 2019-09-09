package com.thoughtworks.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContentModelNotFoundException extends RuntimeException {
    public ContentModelNotFoundException(Long contentModelId) {
        super(String.format("Cannot find content model [%d]", contentModelId));
    }
}
