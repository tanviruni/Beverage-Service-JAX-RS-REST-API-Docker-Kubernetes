package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"id","bottle","noOfBottles","price","inStock"})

public class CrateDTO {

    private int id;
    @XmlElement(required = true)

    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public int getId() {
        return id;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public CrateDTO(){

    }

    public CrateDTO(final Crate cr) {
        this.id = cr.getId();
        this.bottle = cr.getBottle();
        this.noOfBottles = cr.getNoOfBottles();
        this.price = cr.getPrice();
        this.inStock = cr.getInStock();

    }

    public static List<CrateDTO> marshall(final List<Crate> crateList) {
        final ArrayList<CrateDTO> crates = new ArrayList<>();
        for(final Crate b : crateList) {
            crates.add(new CrateDTO(b));
        }
        return crates;
    }

    public Crate unmarshall(){
        return new Crate(this.id, this.bottle, this.noOfBottles, this.price, this.inStock);
    }

    @Override
    public String toString() {
        return "Crate{" +
                "id=" + id +
                ", bottle=" + bottle +
                ", noOfBottles=" + noOfBottles +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }

}
