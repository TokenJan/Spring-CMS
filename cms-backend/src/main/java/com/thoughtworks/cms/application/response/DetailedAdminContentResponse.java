package com.thoughtworks.cms.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetailedAdminContentResponse {

    private Long id;

    private String name;

    private ContentStatus status;

    private List<ContentAttribute> contentAttributeList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public static PropertyMap<Content, DetailedAdminContentResponse> DetailedAdminContentDtoMap = new PropertyMap<Content, DetailedAdminContentResponse>() {

        @Override
        protected void configure() {
            map().setName(source.mustGetDraft().getName());
            map().setContentAttributeList(source.mustGetDraft().getContentAttributeList());
        }
    };

    public static PropertyMap<Content, DetailedAdminContentResponse> CustomerDetailedContentDtoMap = new PropertyMap<Content, DetailedAdminContentResponse>() {

        @Override
        protected void configure() {
            map().setName(source.mustGetPublished().getName());
            map().setContentAttributeList(source.mustGetPublished().getContentAttributeList());
        }
    };
}
