package com.thoughtworks.cms.application.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContentAttributeCommand {

    private String key;

    private String value;

    private String type;
}
