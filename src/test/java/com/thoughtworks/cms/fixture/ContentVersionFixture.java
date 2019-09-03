package com.thoughtworks.cms.fixture;

import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import com.thoughtworks.cms.domain.ContentVersion;

import java.time.LocalDateTime;
import java.util.List;

public class ContentVersionFixture {

    private ContentVersion contentVersion = new ContentVersion();

    public ContentVersionFixture withId(Long id) {
        contentVersion.setId(id);
        return this;
    }

    public ContentVersionFixture withName(String name) {
        contentVersion.setName(name);
        return this;
    }

    public ContentVersionFixture withContent(Content content) {
        contentVersion.setContent(content);
        return this;
    }


    public ContentVersionFixture withContentStatus(ContentStatus contentStatus) {
        contentVersion.setStatus(contentStatus);
        return this;
    }

    public ContentVersionFixture withContentAttribute(List<ContentAttribute> contentAttributeList) {
        contentVersion.setContentAttributeList(contentAttributeList);
        return this;
    }

    public ContentVersion build() {
        contentVersion.setVersion(1L);
        contentVersion.setCreatedTime(LocalDateTime.now());
        contentVersion.setUpdatedTime(LocalDateTime.now());
        return contentVersion;
    }
}
