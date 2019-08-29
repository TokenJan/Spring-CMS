package com.thoughtworks.cms.service;

import com.thoughtworks.cms.command.AbstractContentCommand;
import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.command.CreateContentCommand;
import com.thoughtworks.cms.command.EditContentCommand;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import com.thoughtworks.cms.domain.ContentVersion;
import com.thoughtworks.cms.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional(rollbackFor = DataException.class)
    public void create(CreateContentCommand createContentCommand) {
        Content content = new Content();
        Set<ContentAttribute> contentAttributeSet = populateContentAttributeSet(createContentCommand);
        ContentVersion contentVersion = new ContentVersion();

        contentVersion.setContent(content);
        contentVersion.setVersion(1L);
        contentVersion.setStatus(ContentStatus.DRAFT);
        contentVersion.setContentAttributes(contentAttributeSet);
        contentVersion.setCreatedTime(LocalDateTime.now());
        contentVersion.setUpdatedTime(LocalDateTime.now());

        content.setType(createContentCommand.getContentType());
        content.setStatus(ContentStatus.DRAFT);
        content.setCreatedTime(LocalDateTime.now());
        content.setUpdatedTime(LocalDateTime.now());
        content.setContentVersion(Collections.singletonList(contentVersion));
        contentRepository.save(content);
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public Content get(Long id) {
        return contentRepository.findContentByIdAndStatus(id, ContentStatus.PUBLISHED);
    }

    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    public Content edit(Long id, EditContentCommand editContentCommand) {
        Set<ContentAttribute> contentAttributeSet = populateContentAttributeSet(editContentCommand);

        Content content = contentRepository.findById(id).orElseThrow(Exception::new);

        Optional<ContentVersion> optionalDraftContentVersion = content.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.DRAFT.equals(contentVersion.getStatus()))
                .findFirst();

        if (optionalDraftContentVersion.isPresent()) {
            ContentVersion contentVersion = optionalDraftContentVersion.get();
            contentVersion.setContentAttributes(contentAttributeSet);
            contentVersion.setVersion(contentVersion.getVersion() + 1);
        } else {
            ContentVersion newContentVersion = new ContentVersion();
            newContentVersion.setVersion(1L);
            newContentVersion.setContent(content);
            newContentVersion.setStatus(ContentStatus.DRAFT);
            newContentVersion.setContentAttributes(contentAttributeSet);
            newContentVersion.setCreatedTime(LocalDateTime.now());
            newContentVersion.setUpdatedTime(LocalDateTime.now());
            content.getContentVersion().add(newContentVersion);
        }
        return content;
    }

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Content publish(Long id) {
        Content content = contentRepository.findById(id).orElseThrow(Exception::new);

        content.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.PUBLISHED.equals(contentVersion.getStatus()))
                .findFirst()
                .ifPresent(contentVersion -> contentVersion.setStatus(ContentStatus.ACHIVED));

        content.getContentVersion().stream()
                .filter(contentVersion -> ContentStatus.DRAFT.equals(contentVersion.getStatus()))
                .findFirst()
                .ifPresent(contentVersion -> {
                    contentVersion.setStatus(ContentStatus.PUBLISHED);
                    content.setPublishId(contentVersion.getId());
                });
        content.setStatus(ContentStatus.PUBLISHED);
        return content;
    }

    @Transactional(rollbackFor = Exception.class)
    public void archive(Long id) {
        contentRepository.findById(id).ifPresent(content -> content.setStatus(ContentStatus.ACHIVED));
    }

    private Set<ContentAttribute> populateContentAttributeSet(AbstractContentCommand contentCommand) {
        Set<ContentAttribute> contentAttributeSet = new HashSet<>();
        contentCommand.getAttributes().forEach(attribute -> {
            ContentAttribute contentAttribute = new ContentAttribute();
            contentAttribute.setAttributeType(attribute.getType());
            contentAttribute.setAttributeKey(attribute.getKey());
            contentAttribute.setAttributeValue(attribute.getValue());
            contentAttributeSet.add(contentAttribute);
        });
        return contentAttributeSet;
    }
}
