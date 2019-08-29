package com.thoughtworks.cms.controller;

import com.thoughtworks.cms.command.CreateContentCommand;
import com.thoughtworks.cms.command.EditContentCommand;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public void createContent(@RequestBody CreateContentCommand createContentCommand) {
        contentService.create(createContentCommand);
    }

    @GetMapping("/{id}")
    public Content getContent(@PathVariable Long id) {
        return contentService.get(id);
    }

    @PostMapping("/{id}/publish")
    public Content publishContent(@PathVariable Long id) {
        return contentService.publish(id);
    }

    @PostMapping("/{id}/edit")
    public Content editContent(@PathVariable Long id, @RequestBody EditContentCommand editContentCommand) {
        return contentService.edit(id, editContentCommand);
    }

    @PostMapping("/{id}/archive")
    public void archiveContent(@PathVariable Long id) {
        contentService.archive(id);
    }
}
