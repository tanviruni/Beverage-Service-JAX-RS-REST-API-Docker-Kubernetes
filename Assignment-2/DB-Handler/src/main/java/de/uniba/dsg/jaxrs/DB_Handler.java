package de.uniba.dsg.jaxrs;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;


import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class DB_Handler {
    public static final DB_Handler instance;// = new DB_Handler();
    private static Properties properties = Configuration.loadProperties();
    private static final Logger logger = Logger.getLogger("DB_Handler");
    private File dbFileBottle;
    private File dbFileCrate;
    private List<Bottle> bottles;
    private List<Crate> crates;

    static {
        instance = new DB_Handler();
    }

    public DB_Handler(){
        this.dbFileBottle = new File(properties.getProperty("bottleFile"));
        if(dbFileBottle.exists()==false) {
            try {
                dbFileBottle.createNewFile();
            } catch (IOException e) {
                logger.warning("Failed to create bottle.json file");
            }
            this.initializeBottleFile();
        }

        this.dbFileCrate = new File(properties.getProperty("crateFile"));
        if(dbFileCrate.exists()==false) {
            try {
                dbFileCrate.createNewFile();
            } catch (IOException e) {
                logger.warning("Failed to create crate.json file");
            }
            this.initializeCrateFile();
        }

        this.bottles = this.readAllBottlesFromDB();
        this.crates = this.readAllCratesFromDB();
    }

    private void initializeBottleFile(){
        DB db = new DB();
        this.bottles = db.getBottles();
        this.persistBottles();
    }

    private void initializeCrateFile(){
        DB db = new DB();
        this.crates = db.getCrates();
        this.persistCrates();
    }

    public List<Bottle> getBottles(){
        return this.bottles;
    }

    public List<Crate> getCrates(){
        return this.crates;
    }

    public Bottle getBottle(int bottleId){
        Bottle bottle = null;

        for(Bottle b: this.bottles)
            if (b.getId() == bottleId)
                return b;
        return bottle;
    }

    public Crate getCrate(int crateId){
        Crate crate = null;

        for(Crate c: this.crates)
            if (c.getId() == crateId)
                return c;
        return crate;
    }

    public void insertBottle(Bottle bottle){
        bottle.setId(this.bottles.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        this.bottles.add(bottle);
        this.persistBottles();
    }

    public void insertCrate(Crate crate){
        crate.setId(this.crates.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        crate.setPrice(crate.getBottle().getPrice()*crate.getNoOfBottles());
        this.crates.add(crate);
        System.out.println(crate);
        this.persistCrates();
    }

    public void deleteBottle(Bottle bottle){
        this.bottles.remove(bottle);
        this.persistBottles();
    }

    public void deleteCrate(Crate crate){
        this.crates.remove(crate);
        this.persistCrates();
    }

    public void updateBottle(final Bottle updatedBottle) {
        Bottle btl = this.getBottle(updatedBottle.getId());
        btl.setName(updatedBottle.getName());
        btl.setInStock(updatedBottle.getInStock());
        btl.setAlcoholic(updatedBottle.isAlcoholic());
        btl.setPrice(updatedBottle.getPrice());
        btl.setSupplier(updatedBottle.getSupplier());
        btl.setVolume(updatedBottle.getVolume());
        btl.setVolumePercent(updatedBottle.getVolumePercent());

        this.persistBottles();
    }

    public void updateCrate(final Crate updatedCrate) {
        Crate crt = this.getCrate(updatedCrate.getId());
        crt.setInStock(updatedCrate.getInStock());
        crt.setPrice(updatedCrate.getPrice());
        crt.setBottle(updatedCrate.getBottle());
        crt.setNoOfBottles(updatedCrate.getNoOfBottles());

        this.persistBottles();
    }

    private void persistBottles(){
        try {
            JsonArray arr = new JsonArray();
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFileBottle));
            for(Bottle b: this.bottles){
                JsonObject obj = new JsonObject();
                obj.put("id", Integer.toString(b.getId()));
                obj.put("name", b.getName());
                obj.put("volume", b.getVolume());
                obj.put("isAlcoholic", Boolean.toString(b.isAlcoholic()));
                obj.put("volumePercent", Double.toString(b.getVolumePercent()));
                obj.put("price", Double.toString(b.getPrice()));
                obj.put("supplier", b.getSupplier());
                obj.put("inStock", Integer.toString(b.getInStock()));
                arr.add(obj);
            }

            Jsoner.serialize(arr, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void persistCrates(){
        try {
            JsonArray arr = new JsonArray();
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFileCrate));
            for(Crate c: this.crates){
                JsonObject obj = new JsonObject();
                obj.put("id", Integer.toString(c.getId()));
                obj.put("bottleId", c.getBottle().getId());
                obj.put("noOfBottles", c.getNoOfBottles());
                obj.put("price", Double.toString(c.getPrice()));
                obj.put("inStock", Integer.toString(c.getInStock()));
                arr.add(obj);
            }

            Jsoner.serialize(arr, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Bottle> readAllBottlesFromDB(){

        List <Bottle> bottles = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbFileBottle));
            JsonArray list = (JsonArray) Jsoner.deserialize(reader);
            list.forEach(b->{
                JsonObject bottle = (JsonObject) b;
                Bottle newBottle = new Bottle(Integer.parseInt(bottle.get("id").toString()),
                        bottle.get("name").toString(),
                        Double.valueOf(bottle.get("volume").toString()),
                        Boolean.getBoolean(bottle.get("isAlcoholic").toString()),
                        Double.parseDouble(bottle.get("volumePercent").toString()),
                        Double.parseDouble(bottle.get("price").toString()),
                        bottle.get("supplier").toString(),
                        Integer.parseInt(bottle.get("inStock").toString())
                );
                bottles.add(newBottle);
            });
        } catch (FileNotFoundException e) {

        } catch (JsonException e) {

        }

        return bottles;
    }

    private List<Crate> readAllCratesFromDB(){

        List <Crate> crates = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbFileCrate));
            JsonArray list = (JsonArray) Jsoner.deserialize(reader);
            list.forEach(b->{
                JsonObject crate = (JsonObject) b;
                Crate newCrate = new Crate(Integer.parseInt(crate.get("id").toString()),
                        this.getBottle(Integer.parseInt(crate.get("bottleId").toString())),
                        Integer.parseInt(crate.get("noOfBottles").toString()),
                        Double.parseDouble(crate.get("price").toString()),
                        Integer.parseInt(crate.get("inStock").toString())
                );
                crates.add(newCrate);
            });
        } catch (FileNotFoundException e) {

        } catch (JsonException e) {

        }

        return crates;
    }

    public List<Crate> searchCratesWithBottle(Bottle bottle){
        List<Crate> crates = new ArrayList<>();
        for (Crate crate: this.crates)
            if (crate.getBottle().getId() == bottle.getId())
                crates.add(crate);
        return crates;
    }

}
