package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Bottle;

import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = {"name", "volume", "isAlcoholic", "volumePercent", "price","supplier","inStock"})
public class BottleCreateDTO {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private double volume;
    @XmlElement(required = true)
    private boolean isAlcoholic;
    @XmlElement(required = true)
    private double volumePercent;
    @XmlElement(required = true)
    private double price;
    @XmlElement(required = true)
    private String supplier;
    @XmlElement(required = true)
    private int inStock;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public BottleCreateDTO(){

    }

    public Bottle unmarshall() {
        return new Bottle(-1, this.name, this.volume,this.isAlcoholic,this.volumePercent,this.price,this.supplier,this.inStock);
    }

    @Override
    public String toString() {
        return "BottleDTO{" +
                ", name=" + this.name +
                ", volume='" + this.volume +
                ", isAlcoholic='" + this.isAlcoholic +
                ", volumePercent=" + this.volumePercent +
                ", price='" + this.price +
                ", supplier='" + this.supplier +
                ", inStock=" + this.inStock +
                //", href=" + this.href +
                '}';
    }
}
