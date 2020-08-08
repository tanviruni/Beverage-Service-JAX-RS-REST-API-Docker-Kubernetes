package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
//import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import java.util.List;
import java.util.Optional;

public class BeverageService {

    public static final BeverageService instance = new BeverageService();

    private final DB db;

    public BeverageService() {
        this.db = DB.db;
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

    public Bottle getBottle(final String name){
        return  this.db.getBottle(name);
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

    public List<Crate> getAllCrates() {
        return this.db.getAllCrates();
    }

    public Crate addCrate(final Crate newCrate) {
        if (newCrate == null){
            return null;
        }

        this.db.addCrateToDb(newCrate);
        return newCrate;
    }

    public Crate getCrate(final int id){
        return this.db.getCrate(id);
    }

    public Crate updateCrate(final int id, final Crate updatedCrate) {

        final Crate cr = this.getCrate(id);

        if (cr == null || updatedCrate == null) {
            return null;
        }
        Optional.ofNullable(updatedCrate.getBottle()).ifPresent(c -> cr.setBottle(c));
        Optional.ofNullable(updatedCrate.getNoOfBottles()).ifPresent(c -> cr.setNoOfBottles(c));
        Optional.ofNullable(updatedCrate.getPrice()).ifPresent(c -> cr.setPrice(c));
        Optional.ofNullable(updatedCrate.getInStock()).ifPresent(c -> cr.setInStock(c));

        return cr;
    }

    public Crate getCrateById(final int crateId) {
        return this.db.getCrateById(crateId);
    }


    public Bottle getBottleById(final int bottleId) {
        return this.db.getBottleById(bottleId);
    }
}
