package com.thoughtworks.cms.adapter.controller;

import com.thoughtworks.cms.application.command.CreateContentCommand;
import com.thoughtworks.cms.application.command.EditContentCommand;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.application.response.DetailedAdminContentResponse;
import com.thoughtworks.cms.application.response.DetailedCustomerContentResponse;
import com.thoughtworks.cms.application.response.SimpleContentResponse;
import com.thoughtworks.cms.application.service.ContentService;
import com.thoughtworks.cms.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ContentController {

    private final ContentService contentService;

    private final ModelMapper modelMapper = ModelMapperUtil.getModelMapper();

    @PostMapping
    public SimpleContentResponse createContent(@RequestBody CreateContentCommand createContentCommand) {
        Content content = contentService.create(createContentCommand);
        return modelMapper.map(content, SimpleContentResponse.class);
    }

    @GetMapping
    public List<SimpleContentResponse> getAllContents() {
        List<Content> contents = contentService.getAll();
        return contents.stream()
                .map(content -> modelMapper.map(content, SimpleContentResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DetailedAdminContentResponse getContent(@PathVariable Long id) {
        Content content = contentService.get(id);
        return modelMapper.map(content, DetailedAdminContentResponse.class);
    }

    @GetMapping("/{id}/customer")
    public DetailedCustomerContentResponse viewContent(@PathVariable Long id) {
        Content content = contentService.view(id);
        return modelMapper.map(content, DetailedCustomerContentResponse.class);
    }

    @PostMapping("/{id}/publish")
    public SimpleContentResponse publishContent(@PathVariable Long id) {
        Content content = contentService.publish(id);
        return modelMapper.map(content, SimpleContentResponse.class);
    }

    @PostMapping("/{id}/edit")
    public SimpleContentResponse editContent(@PathVariable Long id, @RequestBody EditContentCommand editContentCommand) {
        Content content = contentService.edit(id, editContentCommand);
        return modelMapper.map(content, SimpleContentResponse.class);
    }

    @PostMapping("/{id}/archive")
    public SimpleContentResponse archiveContent(@PathVariable Long id) {
        Content content = contentService.archive(id);
        return modelMapper.map(content, SimpleContentResponse.class);
    }

    @PostMapping("/{id}/discard")
    public SimpleContentResponse discardContent(@PathVariable Long id) {
        Content content = contentService.discard(id);
        return modelMapper.map(content, SimpleContentResponse.class);
    }
}
