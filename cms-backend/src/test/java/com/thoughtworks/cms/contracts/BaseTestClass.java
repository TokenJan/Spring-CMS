package com.thoughtworks.cms.contracts;

import com.thoughtworks.cms.CmsApplication;
import com.thoughtworks.cms.adapter.controller.ContentModelController;
import com.thoughtworks.cms.adapter.persistence.ContentModelRepository;
import com.thoughtworks.cms.adapter.persistence.ContentRepository;
import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.application.service.ContentService;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import com.thoughtworks.cms.domain.ContentModel;
import com.thoughtworks.cms.domain.ContentVersion;
import com.thoughtworks.cms.fixture.ContentAttributeFixture;
import com.thoughtworks.cms.fixture.ContentFixture;
import com.thoughtworks.cms.fixture.ContentVersionFixture;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = {CmsApplication.class})
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseTestClass {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ContentService contentService;

    @MockBean
    private ContentModelController contentModelController;

    @MockBean
    private ContentModelRepository contentModelRepository;

    @MockBean
    private ContentRepository contentRepository;

    @Before
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        ContentAttribute contentAttribute = new ContentAttributeFixture()
                .withKey("name").withValue("this is title").withType("Text").build();

        ContentVersion contentVersion = new ContentVersionFixture()
                .withId(1L).withContentAttribute(Collections.singletonList(contentAttribute))
                .withContentStatus(ContentStatus.DRAFT).withName("this is title").build();

        Content content = new ContentFixture()
                .withId(1L).withType("Brand").withContentStatus(ContentStatus.DRAFT)
                .withContentVersionList(Collections.singletonList(contentVersion)).build();

        when(contentService.get(1L)).thenReturn(content);
    }
}