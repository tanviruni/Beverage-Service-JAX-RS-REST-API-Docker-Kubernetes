package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cratePostDto")
@XmlType(propOrder = {"id","bottle","noOfBottles","price","inStock"})

public class CrateUpdateDTO {

    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public Bottle getBottle(){
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

    public Crate unmarshall() {
        return new Crate(0, this.bottle, this.noOfBottles, this.price, this.inStock);
    }

    @Override
    public String toString() {
        return "CrateDTO{" +
                ", bottle=" + this.bottle +
                ", noOfBottles='" + this.noOfBottles +
                ", price='" + this.price +
                ", inStock=" + this.inStock +
                '}';
    }
}
