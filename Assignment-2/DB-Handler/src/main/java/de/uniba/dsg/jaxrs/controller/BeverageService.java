package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.DB_Handler;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class BeverageService {
    public static final BeverageService instance = new BeverageService();
    private DB_Handler dbHandler;
    private static final Logger logger = Logger.getLogger("BeverageService");

    public BeverageService() {
        dbHandler = new DB_Handler();
    }

    public List<Bottle> getAllBottles() {
        return this.dbHandler.getBottles();
    }

    public List<Crate> getAllCrates() { return  this.dbHandler.getCrates(); }

}
