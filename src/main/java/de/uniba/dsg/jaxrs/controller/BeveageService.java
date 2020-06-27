package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;

import java.util.List;

public class BeveageService {

    public static final BeveageService instance = new BeveageService();

    private final DB db;

    public BeveageService() {
        this.db = new DB();
    }

    public List<Bottle> getAllBottles() {
        return this.db.getAllBottles();
    }


    public Bottle addBottle(final Bottle newBottle) {
        if (newBottle == null) {
            return null;
        }

        this.db.addBottleToDb(newBottle);
        return newBottle;
    }
}
