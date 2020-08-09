package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.DB_Handler;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import java.util.List;

public class BeverageDeleteService {
    public static final BeverageDeleteService instance = new BeverageDeleteService();

    public BeverageDeleteService(){ }

    public void  deleteBottle(Bottle bottle){
        DB_Handler.instance.deleteBottle(bottle);
    }

    public void  deleteCrate(Crate crate) {
        DB_Handler.instance.deleteCrate(crate);
    }

    public List<Crate> searchCratesWithBottle(Bottle bottle) { return DB_Handler.instance.searchCratesWithBottle(bottle); }
}
