package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.OrderItem;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
@XmlType(propOrder = {"number", "beverage", "quantity"})
public class OrderItemDTO {
    @XmlElement(required = true)
    private int number;
    private Beverage beverage;
    private int quantity;

    public OrderItemDTO(){}

    public OrderItemDTO(OrderItem item){
        this.beverage = item.getBeverage();
        this.number = item.getNumber();
        this.quantity = item.getQuantity();
    }

    public static List<OrderItemDTO> marshall(List<OrderItem> items){
        List<OrderItemDTO> itemDTOS = new ArrayList<OrderItemDTO>();
        for(OrderItem item: items) itemDTOS.add(new OrderItemDTO(item));
        return itemDTOS;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "number=" + number +
                ", beverage=" + beverage +
                ", quantity=" + quantity +
                '}';
    }
}
