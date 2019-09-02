package com.thoughtworks.cms.controller;

import com.thoughtworks.cms.command.CreateContentCommand;
import com.thoughtworks.cms.command.EditContentCommand;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.dto.DetailedContentDto;
import com.thoughtworks.cms.dto.SimpleContentDto;
import com.thoughtworks.cms.service.ContentService;
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
    private ModelMapper adminModelMapper = ModelMapperUtil.getAdminModelMapper();
    private ModelMapper customerModelMapper = ModelMapperUtil.getCustomerModelMapper();

    @PostMapping
    public SimpleContentDto createContent(@RequestBody CreateContentCommand createContentCommand) {
        Content content = contentService.create(createContentCommand);
        return adminModelMapper.map(content, SimpleContentDto.class);
    }

    @GetMapping
    public List<SimpleContentDto> getAllContents() {
        List<Content> contents = contentService.getAll();
        return contents.stream()
                .map(content -> adminModelMapper.map(content, SimpleContentDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DetailedContentDto getContent(@PathVariable Long id) {
        Content content = contentService.get(id);
        return adminModelMapper.map(content, DetailedContentDto.class);
    }

    @PostMapping("/{id}/publish")
    public SimpleContentDto publishContent(@PathVariable Long id) {
        Content content = contentService.publish(id);
        return adminModelMapper.map(content, SimpleContentDto.class);
    }

    @PostMapping("/{id}/edit")
    public SimpleContentDto editContent(@PathVariable Long id, @RequestBody EditContentCommand editContentCommand) {
        Content content = contentService.edit(id, editContentCommand);
        return adminModelMapper.map(content, SimpleContentDto.class);
    }

    @PostMapping("/{id}/archive")
    public SimpleContentDto archiveContent(@PathVariable Long id) {
        Content content = contentService.archive(id);
        return adminModelMapper.map(content, SimpleContentDto.class);
    }

    @PostMapping("/{id}/discard")
    public SimpleContentDto discardContent(@PathVariable Long id) {
        Content content = contentService.discard(id);
        return adminModelMapper.map(content, SimpleContentDto.class);
    }

    @GetMapping("/{id}/customer")
    public DetailedContentDto viewContent(@PathVariable Long id) {
        Content content = contentService.view(id);
        return customerModelMapper.map(content, DetailedContentDto.class);
    }
}
