package com.thoughtworks.cms.dto;

import com.thoughtworks.cms.command.ContentStatus;
import com.thoughtworks.cms.domain.Content;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.PropertyMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class SimpleContentDto {

    private Long id;

    private String name;

    private ContentStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public static PropertyMap<Content, SimpleContentDto> ContentToSimpleContentDtoMap = new PropertyMap<Content, SimpleContentDto>() {

        @Override
        protected void configure() {
            map().setName(source.getDraft().getName());
        }
    };
}
