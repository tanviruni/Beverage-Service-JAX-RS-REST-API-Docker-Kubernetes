package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.BeverageType;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.OrderItem;

import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
@XmlType(propOrder = {"orderNumber", "type", "beverageId", "price", "quantity", "href"})
public class OrderItemDTO {
    @XmlElement(required = true)
    private int orderNumber;
    private int quantity;
    private BeverageType type;
    private int beverageId;
    private double price;
    private URI href;

    public OrderItemDTO(){}

    public OrderItemDTO(OrderItem item){
        //this.beverage = item.getBeverage();
        this.orderNumber = item.getNumber();
        this.quantity = item.getQuantity();
        this.type = item.getBeverage().getType();
        this.beverageId = item.getBeverage().getId();
        this.price = item.getBeverage().getPrice();
    }

    public static List<OrderItemDTO> marshall(List<OrderItem> items){
        List<OrderItemDTO> itemDTOS = new ArrayList<OrderItemDTO>();
        for(OrderItem item: items) itemDTOS.add(new OrderItemDTO(item));
        return itemDTOS;
    }

    public OrderItem unmarshall(){
        Beverage orderItemBeverage = null;
        if(this.type ==  BeverageType.BOTTLE_TYPE)
            orderItemBeverage = BeverageService.instance.getBottle(this.beverageId);
        else
            orderItemBeverage = BeverageService.instance.getCrate(this.beverageId);

        OrderItem orderItem = new OrderItem(this.orderNumber, orderItemBeverage, this.quantity);

        return  orderItem;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

//    public Beverage getBeverage() {
//        return beverage;
//    }
//
//    public void setBeverage(Beverage beverage) {
//        this.beverage = beverage;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BeverageType getType() {
        return type;
    }

    public void setType(BeverageType type) {
        this.type = type;
    }

    public int getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(int beverageId) {
        this.beverageId = beverageId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
    //    @Override
//    public String toString() {
//        return "OrderItemDTO{" +
//                "number=" + orderNumber +
//                ", beverage=" + beverage +
//                ", quantity=" + quantity +
//                '}';
//    }


    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "orderNumber=" + orderNumber +
                ", quantity=" + quantity +
                ", type=" + type +
                ", beverageId=" + beverageId +
                ", price=" + price +
                ", href=" + href +
                '}';
    }
}
