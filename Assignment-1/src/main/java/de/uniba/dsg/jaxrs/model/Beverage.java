package de.uniba.dsg.jaxrs.model;

import java.net.URI;

public interface Beverage {
    BeverageType getType();
    int getId();
    URI getHref();
    double getPrice();
}
