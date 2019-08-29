package com.thoughtworks.cms.repository;

import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.domain.ContentVersion;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContentVersionRepository extends CrudRepository<ContentVersion, Long> {

}
