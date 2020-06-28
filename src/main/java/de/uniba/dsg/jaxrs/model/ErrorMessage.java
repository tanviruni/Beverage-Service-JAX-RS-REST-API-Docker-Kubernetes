package de.uniba.dsg.jaxrs.model;

import javax.xml.bind.annotation.XmlElement;

public class ErrorMessage{

    @XmlElement(required = true)
    private ErrorType errorType;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(final ErrorType errorType, final String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorMessage(final ErrorType errorType){
        this.errorType = errorType;
        this.message = errorType.getMessage();
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(final ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorType=" + this.errorType +
                ", message='" + this.message + '\'' +
                '}';
    }
}