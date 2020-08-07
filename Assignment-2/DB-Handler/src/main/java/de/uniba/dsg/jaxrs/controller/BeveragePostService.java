package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.DB_Handler;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.ws.rs.Path;
import java.util.logging.Logger;


public class BeveragePostService {
    public static final BeveragePostService instance = new BeveragePostService();

    public BeveragePostService() { }

    public void addBottle(Bottle bottle){
        DB_Handler.instance.insertBottle(bottle);
    }

    public void addCrate(Crate crate){
        DB_Handler.instance.insertCrate(crate);
    }
}
