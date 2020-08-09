package de.uniba.dsg.jaxrs.dto;


import de.uniba.dsg.jaxrs.model.Bottle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Optional;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottlePostDto")
@XmlType(propOrder = {"name", "volume", "isAlcoholic", "volumePercent","price","supplier","inStock"})

public class BottleUpdateDTO {
    private String name;
    private Double volume;
    private Boolean isAlcoholic;
    private Double volumePercent;
    private Double price;
    private String supplier;
    private Integer inStock;

    public BottleUpdateDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(Boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public Double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(Double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Bottle unmarshall(Bottle originalBottle) {
        Optional.ofNullable(this.getName()).ifPresent(d -> originalBottle.setName(d));
        Optional.ofNullable(this.getVolume()).ifPresent(d -> originalBottle.setVolume(d.doubleValue()));
        Optional.ofNullable(this.getVolumePercent()).ifPresent(d -> originalBottle.setVolumePercent(d.doubleValue()));
        Optional.ofNullable(this.getPrice()).ifPresent(d -> originalBottle.setPrice(d.doubleValue()));
        Optional.ofNullable(this.getSupplier()).ifPresent(d -> originalBottle.setSupplier(d));
        Optional.ofNullable(this.isAlcoholic()).ifPresent(d -> originalBottle.setAlcoholic(d.booleanValue()));
        Optional.ofNullable(this.getInStock()).ifPresent(d -> originalBottle.setInStock(d.intValue()));


        return originalBottle;//new Bottle(originalBottle.getId(), this.name, this.volumePercent,this.isAlcoholic, this.volume, this.price,this.supplier,this.inStock);
    }

    @Override
    public String toString() {
        return "BottleUpdateDTO{" +
                ", name=" + this.name +
                ", volume='" + this.volume +
                ", isAlcoholic='" + this.isAlcoholic +
                ", volumePercent=" + this.volumePercent +
                ", price='" + this.price +
                ", supplier='" + this.supplier +
                ", inStock=" + this.inStock +
                '}';
    }


}
