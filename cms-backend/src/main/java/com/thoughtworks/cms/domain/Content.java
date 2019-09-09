package com.thoughtworks.cms.domain;

import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.exception.DraftNotFoundException;
import com.thoughtworks.cms.exception.PublishNotFoundException;
import com.thoughtworks.cms.utils.ModelMapperUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.modelmapper.ModelMapper;

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
import java.util.Optional;

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
        this.mustGetDraft().update(contentAttributeList);
        if (ContentStatus.PUBLISHED.equals(this.getStatus())) {
            this.setStatus(ContentStatus.CHANGED);
        }
    }

    public void publish() {
        this.setStatus(ContentStatus.PUBLISHED);
        this.setUpdatedTime(LocalDateTime.now());
        this.getOptionalPublished().ifPresent(publishedContent -> publishedContent.setStatus(ContentStatus.ACHIVED));
        List<ContentAttribute> contentAttributeList = populateContentAttributeList(this.mustGetDraft().getContentAttributeList());
        ContentVersion contentVersion = new ContentVersion(this, contentAttributeList).create();
        contentVersion.setStatus(ContentStatus.PUBLISHED);
        this.getContentVersion().add(contentVersion);
    }

    public void discard() {
        List<ContentAttribute> contentAttributeList = populateContentAttributeList(this.mustGetPublished().getContentAttributeList());
        this.mustGetDraft().update(contentAttributeList);
        this.updatedTime = LocalDateTime.now();
        this.status = ContentStatus.PUBLISHED;
    }

    public void archive() {
        this.status = ContentStatus.ACHIVED;
        this.updatedTime = LocalDateTime.now();
    }

    public ContentVersion mustGetDraft() {
        return this.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.DRAFT.equals(contentVersion.getStatus()))
                .findFirst()
                .orElseThrow(() -> new DraftNotFoundException(this.getId()));
    }

    public ContentVersion mustGetPublished() {
        return this.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.PUBLISHED.equals(contentVersion.getStatus()))
                .findFirst()
                .orElseThrow(() -> new PublishNotFoundException(this.getId()));
    }

    private Optional<ContentVersion> getOptionalPublished() {
        return this.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.PUBLISHED.equals(contentVersion.getStatus()))
                .findFirst();
    }

    private List<ContentAttribute> populateContentAttributeList(List<ContentAttribute> contentAttributeList) {
        ModelMapper modelMapper = ModelMapperUtil.getModelMapper();
        List<ContentAttribute> newContentAttributeList = new ArrayList<>();
        contentAttributeList.forEach(attribute ->
            newContentAttributeList.add(modelMapper.map(attribute, ContentAttribute.class)));
        return newContentAttributeList;
    }
}