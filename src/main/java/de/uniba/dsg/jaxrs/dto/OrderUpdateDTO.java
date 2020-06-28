package de.uniba.dsg.jaxrs.dto;


import com.sun.org.apache.xpath.internal.operations.Or;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.OrderItem;
import de.uniba.dsg.jaxrs.model.OrderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderPostDto")
@XmlType(propOrder = {"status", "positions"})

public class OrderUpdateDTO {
    //private double price;
    private OrderStatus status;
    private List<OrderItemDTO> positions;

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

    public Order unmarshall(){
        List<OrderItem> list = new ArrayList<OrderItem>();
        for(OrderItemDTO dto: this.positions)
            list.add(dto.unmarshall());
        return new Order(0, list, 0, this.status);
    }

    @Override
    public String toString() {
        return "OrderUpdateDTO{" +
                "status=" + status +
                ", positions=" + positions +
                '}';
    }
}
