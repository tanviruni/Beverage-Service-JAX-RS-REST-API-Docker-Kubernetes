package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;

import java.util.List;
import java.util.Optional;

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

    public Bottle getBottle(final int id) {
        return this.db.getBottle(id);
    }

    public Bottle updateBottle(final int id, final Bottle updatedBottle) {
        // db access :) - here we work on the reference, so no storing is needed at the end :)
        final Bottle btl = this.getBottle(id);

        if (btl == null || updatedBottle == null) {
            return null;
        }
        Optional.ofNullable(updatedBottle.getName()).ifPresent(d -> btl.setName(d));
        Optional.ofNullable(updatedBottle.getVolume()).ifPresent(d -> btl.setVolume(d));
        Optional.ofNullable(updatedBottle.getVolumePercent()).ifPresent(d -> btl.setVolumePercent(d));
        Optional.ofNullable(updatedBottle.getPrice()).ifPresent(d -> btl.setPrice(d));
        Optional.ofNullable(updatedBottle.getSupplier()).ifPresent(d -> btl.setSupplier(d));
        Optional.ofNullable(updatedBottle.isAlcoholic()).ifPresent(d -> btl.setAlcoholic(d));
        Optional.ofNullable(updatedBottle.getInStock()).ifPresent(d -> btl.setInStock(d));


        return btl;
    }
}
