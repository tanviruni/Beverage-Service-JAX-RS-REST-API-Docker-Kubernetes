package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.DB_Handler;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import java.util.List;
import java.util.logging.Logger;

public class BeverageGetService {
    public static final BeverageGetService instance = new BeverageGetService();

    public BeverageGetService() { }

    public List<Bottle> getAllBottles() {
        return DB_Handler.instance.getBottles();
    }

    public List<Crate> getAllCrates() { return  DB_Handler.instance.getCrates(); }

    public Bottle getBottleById(int id) { return DB_Handler.instance.getBottle(id); }

    public Crate getCrateById(int id) {
        return DB_Handler.instance.getCrate(id);
    }

}
