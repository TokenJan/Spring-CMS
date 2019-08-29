package com.thoughtworks.cms.repository;

import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, Long> {

    Content findContentByIdAndStatus(Long id, ContentStatus status);
}
