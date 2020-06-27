package de.uniba.dsg.jaxrs.dto;

import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderStatus;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = {"id", "status", "price", "positions"})
public class OrderDTO {
    @XmlElement(required = true)
    private int id;
    private double price;
    private OrderStatus status;
    private List<OrderItemDTO> positions;

    public OrderDTO(){}

    public OrderDTO(Order order) {
        this.id = order.getOrderId();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.positions = OrderItemDTO.marshall(order.getPositions());
    }

    public static List<OrderDTO> marshall(final List<Order> orders){
        final List<OrderDTO> ordList = new ArrayList<OrderDTO>();
        for (Order o: orders) ordList.add(new OrderDTO(o));
        return ordList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItemDTO> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", price=" + price +
                ", status=" + status +
                ", positions=" + positions +
                '}';
    }
}
