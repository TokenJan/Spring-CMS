package com.thoughtworks.cms.application.service;

import com.thoughtworks.cms.application.command.AbstractContentCommand;
import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.application.command.CreateContentCommand;
import com.thoughtworks.cms.application.command.EditContentCommand;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import com.thoughtworks.cms.exception.ContentNotFoundException;
import com.thoughtworks.cms.adapter.persistence.ContentRepository;
import com.thoughtworks.cms.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    private final ModelMapper modelMapper = ModelMapperUtil.getModelMapper();

    @Transactional(rollbackFor = DataException.class)
    public Content create(CreateContentCommand createContentCommand) {
        List<ContentAttribute> contentAttributeList = populateContentAttributeList(createContentCommand);
        Content content = new Content(createContentCommand.getContentType(), contentAttributeList).create();
        contentRepository.save(content);
        return content;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public Content get(Long id) {
        return contentRepository.findByIdAndStatusIsNot(id, ContentStatus.ACHIVED).orElseThrow(() -> new ContentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Content view(Long id) {
        return contentRepository.findByIdAndStatusIn(id, Arrays.asList(ContentStatus.PUBLISHED, ContentStatus.CHANGED)).orElseThrow(() -> new ContentNotFoundException(id));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @SneakyThrows
    public Content edit(Long id, EditContentCommand editContentCommand) {
        List<ContentAttribute> contentAttributeList = populateContentAttributeList(editContentCommand);
        Content content = contentRepository.findByIdAndStatusIsNot(id, ContentStatus.ACHIVED).orElseThrow(() -> new ContentNotFoundException(id));
        content.update(contentAttributeList);
        return content;
    }

    @SneakyThrows
    @Transactional(rollbackFor = RuntimeException.class)
    public Content publish(Long id) {
        Content content = contentRepository.findByIdAndStatusIsNot(id, ContentStatus.ACHIVED).orElseThrow(() -> new ContentNotFoundException(id));
        content.publish();
        return content;
    }

    @SneakyThrows
    @Transactional(rollbackFor = RuntimeException.class)
    public Content discard(Long id) {
        Content content = contentRepository.findByIdAndStatusIsNot(id, ContentStatus.ACHIVED).orElseThrow(() -> new ContentNotFoundException(id));
        content.discard();
        return content;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Content archive(Long id) {
        Content content = contentRepository.findByIdAndStatusIsNot(id, ContentStatus.ACHIVED).orElseThrow(() -> new ContentNotFoundException(id));
        content.archive();
        return content;
    }

    @Transactional(readOnly = true)
    public List<Content> getAll() {
        return contentRepository.findByStatusIsNot(ContentStatus.ACHIVED);
    }

    private List<ContentAttribute> populateContentAttributeList(AbstractContentCommand contentCommand) {
        List<ContentAttribute> contentAttributeList = new ArrayList<>();
        contentCommand.getAttributes().forEach(attribute ->
            contentAttributeList.add(modelMapper.map(attribute, ContentAttribute.class)));
        return contentAttributeList;
    }

}
