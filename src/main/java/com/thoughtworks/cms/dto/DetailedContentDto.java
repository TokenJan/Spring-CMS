package com.thoughtworks.cms.dto;

import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.PropertyMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetailedContentDto {

    private Long id;

    private String name;

    private ContentStatus status;

    private List<ContentAttribute> contentAttributeList;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public static PropertyMap<Content, DetailedContentDto> AdminDetailedContentDtoMap = new PropertyMap<Content, DetailedContentDto>() {

        @Override
        protected void configure() {
            map().setName(source.getDraft().getName());
            map().setContentAttributeList(source.getDraft().getContentAttributeList());
        }
    };

    public static PropertyMap<Content, DetailedContentDto> CustomerDetailedContentDtoMap = new PropertyMap<Content, DetailedContentDto>() {

        @Override
        protected void configure() {
            map().setName(source.getPublish().getName());
            map().setContentAttributeList(source.getPublish().getContentAttributeList());
        }
    };
}
