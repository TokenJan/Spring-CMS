package com.thoughtworks.cms.fixture;

import com.thoughtworks.cms.domain.ContentAttribute;

public class ContentAttributeFixture {

    private ContentAttribute contentAttribute = new ContentAttribute();

    public ContentAttributeFixture withKey(String key) {
        contentAttribute.setKey(key);
        return this;
    }

    public ContentAttributeFixture withValue(String value) {
        contentAttribute.setValue(value);
        return this;
    }

    public ContentAttributeFixture withType(String type) {
        contentAttribute.setType(type);
        return this;
    }

    public ContentAttribute build() {
        return contentAttribute;
    }
}
