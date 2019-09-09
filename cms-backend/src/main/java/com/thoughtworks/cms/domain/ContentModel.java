package com.thoughtworks.cms.domain;

import com.thoughtworks.cms.application.converter.HashMapConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ContentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Convert(converter = HashMapConverter.class)
    private Map<String, String> attributes;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime;

    public ContentModel(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public ContentModel create() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
        return this;
    }

    public void update(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
