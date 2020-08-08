package de.uniba.dsg.jaxrs.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum ErrorType {
    INVALID_PARAMETER ("Invalid Parameter", 2500),
    ITEM_NOT_FOUND ("The item is not in the database", 2501),
    INSUFFICIENT_STOCK("There is not enough beverages in stock", 2502),
    INSERT_SUCCESSFUL ("Data successfully inserted", 2505),
    LINKED_WITH_CRATES("The bottle is used in crate/crates", 2506)
    ;
    private final String message;
    private final int code;

    ErrorType(String messagesg, int code){
        this.code = code;
        this.message = messagesg;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
