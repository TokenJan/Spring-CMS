package com.thoughtworks.cms.adapter.persistence;

import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import com.thoughtworks.cms.domain.ContentVersion;
import com.thoughtworks.cms.fixture.ContentAttributeFixture;
import com.thoughtworks.cms.fixture.ContentFixture;
import com.thoughtworks.cms.fixture.ContentVersionFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ContentRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void setup() {
        ContentAttribute contentAttribute = new ContentAttributeFixture()
                .withKey("name").withValue("this is title").withType("Text").build();

        ContentVersion contentVersion = new ContentVersionFixture()
                .withContentAttribute(Collections.singletonList(contentAttribute))
                .withContentStatus(ContentStatus.DRAFT).withName("this is title").build();

        Content content = new ContentFixture()
                .withType("Brand").withContentStatus(ContentStatus.DRAFT)
                .withContentVersionList(Collections.singletonList(contentVersion)).build();

        testEntityManager.persist(content);
    }

    @Test
    public void should_return_all_non_archived_contents_successfully() {
        Page<Content> contentPage = contentRepository.findContentPageByContentStatus(new PageRequest(0, 10), ContentStatus.ACHIVED);
        assert contentPage.getTotalElements() == 1L;
    }

}