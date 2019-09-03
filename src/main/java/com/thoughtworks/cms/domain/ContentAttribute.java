package com.thoughtworks.cms.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ContentAttribute {

    private String key;

    private String value;

    private String type;
}
