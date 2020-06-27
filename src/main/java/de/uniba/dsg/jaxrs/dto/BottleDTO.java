package de.uniba.dsg.jaxrs.dto;
import de.uniba.dsg.jaxrs.model.Bottle;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cat")
@XmlType(propOrder = {"id", "name", "volume", "isAlcoholic", "volumePercent", "price","supplier","inStock"})
public class BottleDTO {
    private int id;
    @XmlElement(required = true)
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public BottleDTO(){

    }

    public BottleDTO(final Bottle bt) {
        this.id = bt.getId();
        this.name = bt.getName();
        this.volume = bt.getVolume();
        this.isAlcoholic = bt.isAlcoholic();
        this.volumePercent = bt.getVolumePercent();
        this.price = bt.getPrice();
        this.supplier = bt.getSupplier();
        this.inStock = bt.getInStock();
    }

    public static List<BottleDTO> marshall(final List<Bottle> btlList) {
        final ArrayList<BottleDTO> btl = new ArrayList<>();
        for (final Bottle m : btlList) {
            btl.add(new BottleDTO(m));
        }
        return btl;
    }

    public Bottle unmarshall() {
        return new Bottle(this.id, this.name, this.volume,this.isAlcoholic,this.volumePercent,this.price,this.supplier,this.inStock);
    }

    @Override
    public String toString() {
        return "BottleDTO{" +
                "id=" + this.id +
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
