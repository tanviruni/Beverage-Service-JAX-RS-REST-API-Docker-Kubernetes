package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"id","bottle","noOfBottles","price","inStock","href"})
public class CrateDTO {
    private int id;
    @XmlElement(required = true)
    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;
    private URI href;


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

    public CrateDTO(final Crate bt, final URI baseUri) {
        this.id = bt.getId();
        this.bottle = bt.getBottle();
        this.noOfBottles = bt.getNoOfBottles();
        this.price = bt.getBottle().getPrice() * bt.getNoOfBottles();
        this.inStock = bt.getInStock();
        this.href = null;//UriBuilder.fromUri(baseUri).path(BeverageResource.class).path(BeverageResource.class, "getCrateById").build("crate?crateId=",this.id);
    }

    public static List<CrateDTO> marshall(final List<Crate> btlList, final URI baseUri) {
        final ArrayList<CrateDTO> btl = new ArrayList<>();
        for (final Crate m : btlList) {
            btl.add(new CrateDTO(m,baseUri));
        }
        return btl;
    }

    public Crate unmarshall() {
        return new Crate(this.id, this.bottle, this.noOfBottles, this.price, this.inStock);
    }

    @Override
    public String toString() {
        return "CrateDTO{" +
                "id=" + this.id +
                ", bottle=" + this.bottle +
                ", noOfBottles='" + this.noOfBottles +
                ", price='" + this.price +
                ", inStock=" + this.inStock +
                ", href=" + this.href +
                '}';
    }

}
