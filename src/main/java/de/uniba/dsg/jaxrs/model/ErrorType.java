package de.uniba.dsg.jaxrs.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum ErrorType {
    INVALID_PARAMETER
}
