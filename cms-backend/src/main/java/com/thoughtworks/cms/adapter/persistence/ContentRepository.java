package com.thoughtworks.cms.adapter.persistence;

import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends CrudRepository<Content, Long> {

    @Query(value = "from Content c where c.id = :id and c.status <> :contentStatus")
    Optional<Content> findContentByIdAndContentStatus(Long id, ContentStatus contentStatus);

    @Query(value = "from Content c where c.id = :id and c.status in :contentStatusList")
    Optional<Content> findContentByIdAndContentStatus(Long id, List<ContentStatus> contentStatusList);

    @Query(value = "from Content c where c.status <> :contentStatus")
    Page<Content> findContentPageByContentStatus(Pageable pageable, ContentStatus contentStatus);
}
