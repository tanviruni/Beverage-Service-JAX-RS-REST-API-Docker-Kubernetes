package de.uniba.dsg.jaxrs.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum BeverageType {
    BOTTLE_TYPE, CRATE_TYPE
}
