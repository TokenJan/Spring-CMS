package com.thoughtworks.cms.adapter.controller;

import com.github.hippoom.wiremock.contract.verifier.anntation.Contract;
import com.github.hippoom.wiremock.contract.verifier.junit.MockMvcContractVerifier;
import com.thoughtworks.cms.adapter.persistence.ContentRepository;
import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.application.service.ContentService;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import com.thoughtworks.cms.domain.ContentVersion;
import com.thoughtworks.cms.fixture.ContentAttributeFixture;
import com.thoughtworks.cms.fixture.ContentFixture;
import com.thoughtworks.cms.fixture.ContentVersionFixture;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(ContentController.class)
@RunWith(SpringRunner.class)
public class ContentControllerTest extends AbstractWebMvcTest{

    @Rule
    public final MockMvcContractVerifier contractVerifier = new MockMvcContractVerifier();

    @MockBean
    private ContentService contentService;

    @MockBean
    private ContentRepository contentRepository;

    @Test
    @Contract("content/admin_view_content_by_id.json")
    public void should_view_all_non_archived_contents_successfully() throws Exception {

        ContentAttribute contentAttribute = new ContentAttributeFixture()
                .withKey("name").withValue("this is title").withType("Text").build();

        ContentVersion contentVersion = new ContentVersionFixture()
                .withId(1L).withContentAttribute(Collections.singletonList(contentAttribute))
                .withContentStatus(ContentStatus.DRAFT).withName("this is title").build();

        Content content = new ContentFixture()
                .withId(1L).withType("Brand").withContentStatus(ContentStatus.DRAFT)
                .withContentVersionList(Collections.singletonList(contentVersion)).build();

        when(contentService.get(1L)).thenReturn(content);

        this.mockMvc.perform(contractVerifier.requestPattern())
                .andExpect(contractVerifier.responseDefinition());
    }
}