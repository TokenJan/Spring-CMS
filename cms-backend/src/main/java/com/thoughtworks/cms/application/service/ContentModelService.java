package com.thoughtworks.cms.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.cms.adapter.persistence.ContentModelRepository;
import com.thoughtworks.cms.application.command.AbstractContentModelCommand;
import com.thoughtworks.cms.domain.ContentModel;
import com.thoughtworks.cms.exception.ContentModelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentModelService {

    private final ContentModelRepository contentModelRepository;

    @Transactional(readOnly = true)
    public ContentModel view(Long id) {
        return contentModelRepository.findById(id).orElseThrow(() -> new ContentModelNotFoundException(id));
    }

    @Transactional(rollbackFor = DataException.class)
    public ContentModel create(AbstractContentModelCommand contentModelCommand) {
        ContentModel contentModel = new ContentModel(contentModelCommand.getName(), contentModelCommand.getAttributes()).create();
        contentModelRepository.save(contentModel);
        return contentModel;
    }

    @Transactional(rollbackFor = DataException.class)
    public ContentModel edit(Long id, AbstractContentModelCommand contentModelCommand) {
        ContentModel contentModel = contentModelRepository.findById(id).orElseThrow(() -> new ContentModelNotFoundException(id));
        contentModel.update(contentModelCommand.getAttributes());
        return contentModel;
    }

    @Transactional(readOnly = true)
    public Page<ContentModel> getAll(Pageable pageable) {
        return contentModelRepository.findAll(pageable);
    }
}