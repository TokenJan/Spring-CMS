package com.thoughtworks.cms.adapter.controller;

import com.thoughtworks.cms.application.command.AbstractContentModelCommand;
import com.thoughtworks.cms.application.response.SimpleContentModelResponse;
import com.thoughtworks.cms.application.service.ContentModelService;
import com.thoughtworks.cms.domain.ContentModel;
import com.thoughtworks.cms.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contentModels")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ContentModelController {

    private final ContentModelService contentModelService;

    private final ModelMapper modelMapper = ModelMapperUtil.getModelMapper();

    @PostMapping
    public SimpleContentModelResponse createContentModel(@RequestBody AbstractContentModelCommand contentModelCommand) {
        ContentModel contentModel = contentModelService.create(contentModelCommand);
        return modelMapper.map(contentModel, SimpleContentModelResponse.class);
    }

    @GetMapping
    public Page<SimpleContentModelResponse> getAllContentModels(@PageableDefault Pageable pageable) {
        Page<ContentModel> contentModels = contentModelService.getAll(pageable);
        return contentModels.map(contentModel -> modelMapper.map(contentModel, SimpleContentModelResponse.class));
    }

    @GetMapping("/{id}")
    public SimpleContentModelResponse viewContentModel(@PathVariable Long id) {
        ContentModel contentModel = contentModelService.view(id);
        return modelMapper.map(contentModel, SimpleContentModelResponse.class);
    }

    @PostMapping("/{id}/edit")
    public SimpleContentModelResponse editContentModel(@PathVariable Long id, @RequestBody AbstractContentModelCommand contentModelCommand) {
        ContentModel contentModel =  contentModelService.edit(id, contentModelCommand);
        return modelMapper.map(contentModel, SimpleContentModelResponse.class);
    }
}
