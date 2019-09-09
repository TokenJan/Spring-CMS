package com.thoughtworks.cms.application.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AbstractContentCommand {

    private List<CreateContentAttributeCommand> attributes;
}
