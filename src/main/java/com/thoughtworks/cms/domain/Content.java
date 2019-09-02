package com.thoughtworks.cms.domain;

import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.exception.DraftNotFoundException;
import com.thoughtworks.cms.exception.PublishNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ContentStatus status;

    private String type;

    @OneToMany(mappedBy = "content")
    @Cascade(CascadeType.ALL)
    private List<ContentVersion> contentVersion;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public Content(String contentType, List<ContentAttribute> contentAttributeList) {
        this.type = contentType;
        ContentVersion contentVersion = new ContentVersion(this, contentAttributeList).create();
        this.contentVersion = Collections.singletonList(contentVersion);
    }

    public Content create() {
        this.status = ContentStatus.DRAFT;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
        return this;
    }

    public void update(List<ContentAttribute> contentAttributeList) {
        this.updatedTime = LocalDateTime.now();
        this.getDraft().update(contentAttributeList);
        if (ContentStatus.PUBLISHED.equals(this.getStatus())) {
            this.setStatus(ContentStatus.CHANGED);
        }
    }

    public void publish() {
        this.setStatus(ContentStatus.PUBLISHED);
        this.setUpdatedTime(LocalDateTime.now());
        List<ContentAttribute> contentAttributeList = populateContentAttributeList(this.getDraft().getContentAttributeList());
        ContentVersion contentVersion = new ContentVersion(this, contentAttributeList).create();
        contentVersion.setStatus(ContentStatus.PUBLISHED);
        this.getContentVersion().add(contentVersion);
    }

    public void discard() {
        List<ContentAttribute> contentAttributeList = populateContentAttributeList(this.getPublish().getContentAttributeList());
        this.getDraft().update(contentAttributeList);
        this.updatedTime = LocalDateTime.now();
        this.status = ContentStatus.PUBLISHED;
    }

    public void archive() {
        this.status = ContentStatus.ACHIVED;
        this.updatedTime = LocalDateTime.now();
    }

    public ContentVersion getDraft() {
        return this.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.DRAFT.equals(contentVersion.getStatus()))
                .findFirst()
                .orElseThrow(() -> new DraftNotFoundException(this.getId()));
    }

    public ContentVersion getPublish() {
        return this.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.PUBLISHED.equals(contentVersion.getStatus()))
                .findFirst()
                .orElseThrow(() -> new PublishNotFoundException(this.getId()));
    }

    private List<ContentAttribute> populateContentAttributeList(List<ContentAttribute> contentAttributeList) {
        List<ContentAttribute> newContentAttributeList = new ArrayList<>();
        contentAttributeList.forEach(attribute -> {
            ContentAttribute contentAttribute = new ContentAttribute();
            contentAttribute.setAttributeType(attribute.getAttributeType());
            contentAttribute.setAttributeKey(attribute.getAttributeKey());
            contentAttribute.setAttributeValue(attribute.getAttributeValue());
            newContentAttributeList.add(contentAttribute);
        });
        return newContentAttributeList;
    }
}