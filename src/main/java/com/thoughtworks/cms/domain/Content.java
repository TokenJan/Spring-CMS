package com.thoughtworks.cms.domain;

import com.thoughtworks.cms.command.ContentStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ContentStatus status;

    private String type;

    @OneToMany(mappedBy = "content")
    @Cascade(CascadeType.ALL)
    private List<ContentVersion> contentVersion;

    private Long publishId;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}