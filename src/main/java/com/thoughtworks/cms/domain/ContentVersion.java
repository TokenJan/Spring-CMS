package com.thoughtworks.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.cms.command.ContentStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class ContentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private Content content;

    private Long version;

    @Enumerated(value = EnumType.STRING)
    private ContentStatus status;

    @ElementCollection
    @CollectionTable(
            name = "content_attribute",
            joinColumns = @JoinColumn(name = "version_id")
    )
    @Cascade(CascadeType.ALL)
    private Set<ContentAttribute> contentAttributes = new HashSet<>();

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}
