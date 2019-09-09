package com.thoughtworks.cms.application.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContentCommand extends AbstractContentCommand {

    private String contentType;
}
