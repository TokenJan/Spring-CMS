package com.thoughtworks.cms.adapter.persistence;

import com.thoughtworks.cms.domain.ContentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ContentModelRepository extends CrudRepository<ContentModel, Long> {

    Page<ContentModel> findAll(Pageable pageable);
}
