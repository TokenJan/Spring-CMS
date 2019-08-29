package com.thoughtworks.cms.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ContentAttribute {

    @Column(name = "attribute_key")
    private String attributeKey;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "attribute_type")
    private String attributeType;
}
