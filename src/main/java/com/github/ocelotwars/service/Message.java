package com.github.ocelotwars.service;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "@type")
@JsonSubTypes({
    @Type(name = "register", value = Register.class),
    @Type(name = "accept", value = Accept.class),
    @Type(name = "commands", value = Commands.class)
})
public interface Message {

}
