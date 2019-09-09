package com.thoughtworks.cms.fixture;

import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentVersion;

import java.time.LocalDateTime;
import java.util.List;


public class ContentFixture {

    private Content content = new Content();

    public ContentFixture withId(Long id) {
        content.setId(id);
        return this;
    }

    public ContentFixture withContentStatus(ContentStatus contentStatus) {
        content.setStatus(contentStatus);
        return this;
    }

    public ContentFixture withType(String type) {
        content.setType(type);
        return this;
    }

    public ContentFixture withContentVersionList(List<ContentVersion> contentVersionList) {
        content.setContentVersion(contentVersionList);
        return this;
    }

    public Content build() {
        content.setCreatedTime(LocalDateTime.now());
        content.setUpdatedTime(LocalDateTime.now());
        return content;
    }
}
