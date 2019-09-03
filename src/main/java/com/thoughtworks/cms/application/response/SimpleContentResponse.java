package com.thoughtworks.cms.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.cms.application.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;

@Getter
@Setter
public class SimpleContentResponse {

    private Long id;

    private String name;

    private ContentStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public static PropertyMap<Content, SimpleContentResponse> ContentToSimpleContentDtoMap = new PropertyMap<Content, SimpleContentResponse>() {

        @Override
        protected void configure() {
            map().setName(source.getDraft().getName());
        }
    };
}
