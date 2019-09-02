package com.thoughtworks.cms.repository;

import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends CrudRepository<Content, Long> {

    Optional<Content> findByIdAndStatusIsNot(Long id, ContentStatus contentStatus);

    Optional<Content> findByIdAndStatusIn(Long id, List<ContentStatus> contentStatusList);

    List<Content> findByStatusIsNot(ContentStatus contentStatus);
}
