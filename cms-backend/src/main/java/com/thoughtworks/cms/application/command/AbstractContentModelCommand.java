package com.thoughtworks.cms.application.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AbstractContentModelCommand {

    private String name;

    private Map<String, String> attributes;
}
