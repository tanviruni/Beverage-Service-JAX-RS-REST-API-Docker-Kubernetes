package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"bottleId","noOfBottles","inStock"})
public class CrateCreateDTO {
    @XmlElement(required = true)
    private int bottleId;
    private int noOfBottles;
    private int inStock;


    public int getBottleId() {
        return bottleId;
    }

    public void setBottleId(int bottleId) {
        this.bottleId = bottleId;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public CrateCreateDTO(){

    }

    public Crate unmarshall(Bottle bottle) {

        return new Crate(-1, bottle, this.noOfBottles, -1, this.inStock);
    }

    @Override
    public String toString() {
        return "CrateDTO{" +
                ", bottleId=" + this.bottleId +
                ", noOfBottles='" + this.noOfBottles +
                ", inStock=" + this.inStock +
                '}';
    }

}
