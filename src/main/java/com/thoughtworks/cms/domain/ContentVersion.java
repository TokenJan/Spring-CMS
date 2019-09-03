package com.thoughtworks.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.cms.application.command.ContentStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class ContentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private Content content;

    private Long version;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private ContentStatus status;

    @ElementCollection
    @CollectionTable(
            name = "content_attribute",
            joinColumns = @JoinColumn(name = "version_id")
    )
    @Cascade(CascadeType.ALL)
    private List<ContentAttribute> contentAttributeList;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public ContentVersion(Content content, List<ContentAttribute> contentAttributeList) {
        this.content = content;
        this.contentAttributeList = contentAttributeList;
    }

    public ContentVersion create() {
        this.version = 1L;
        this.status = ContentStatus.DRAFT;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
        this.updateName();
        return this;
    }

    public void update(List<ContentAttribute> contentAttributeList) {
        this.version++;
        this.contentAttributeList = contentAttributeList;
        this.updateName();
        this.updatedTime = LocalDateTime.now();
    }

    private void updateName() {
        this.contentAttributeList.stream()
                .filter(contentAttribute -> "name".equals(contentAttribute.getKey()))
                .findAny()
                .ifPresent(contentAttribute -> this.name = contentAttribute.getValue());
    }
}
