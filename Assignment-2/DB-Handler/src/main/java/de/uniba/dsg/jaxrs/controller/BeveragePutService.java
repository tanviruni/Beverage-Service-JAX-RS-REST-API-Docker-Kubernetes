package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.DB_Handler;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

public class BeveragePutService {
    public static final BeveragePutService instance = new BeveragePutService();

    public void updateBottle(Bottle bottle){
        DB_Handler.instance.updateBottle(bottle);
    }
    public void updateCrate(Crate crate){
        DB_Handler.instance.updateCrate(crate);
    }
}
