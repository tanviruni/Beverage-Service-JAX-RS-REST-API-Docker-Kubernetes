package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Optional;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cratePostDto")
@XmlType(propOrder = {"bottleId","noOfBottles","inStock"})

public class CrateUpdateDTO {

    private Integer bottleId;
    private Integer noOfBottles;
    private Integer inStock;

    public Integer getBottleId(){
        return bottleId;
    }

    public void setBottleId(Integer bottleId) {
        this.bottleId = bottleId;
    }

    public Integer getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(Integer noOfBottles) {
        this.noOfBottles = noOfBottles;
    }


    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Crate unmarshall(Crate originalCrate, Bottle updatedBottle) {
        Optional.ofNullable(this.getBottleId()).ifPresent(d -> {
            originalCrate.setBottle(updatedBottle);
            originalCrate.setPrice(updatedBottle.getPrice()*originalCrate.getNoOfBottles());
        });
        Optional.ofNullable(this.getNoOfBottles()).ifPresent(d -> {
            originalCrate.setNoOfBottles(d.intValue());
            originalCrate.setPrice(originalCrate.getBottle().getPrice()*originalCrate.getNoOfBottles());
        });
        Optional.ofNullable(this.getInStock()).ifPresent(d -> originalCrate.setInStock(d.intValue()));

        return originalCrate;
    }

    @Override
    public String toString() {
        return "CrateDTO{" +
                ", bottle=" + this.bottleId +
                ", noOfBottles='" + this.noOfBottles +
                ", inStock=" + this.inStock +
                '}';
    }
}
