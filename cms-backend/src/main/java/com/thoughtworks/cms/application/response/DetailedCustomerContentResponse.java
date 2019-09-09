package com.thoughtworks.cms.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.cms.domain.Content;
import com.thoughtworks.cms.domain.ContentAttribute;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetailedCustomerContentResponse {

    private Long id;

    private String name;

    private List<ContentAttribute> contentAttributeList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public static PropertyMap<Content, DetailedCustomerContentResponse> DetailedCustomerContentDtoMap = new PropertyMap<Content, DetailedCustomerContentResponse>() {

        @Override
        protected void configure() {
            map().setName(source.mustGetPublished().getName());
            map().setContentAttributeList(source.mustGetPublished().getContentAttributeList());
        }
    };

}
