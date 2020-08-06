package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.*;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.List;
import java.util.Optional;

public class OrderService {
    public static final OrderService instance = new OrderService();

    private final DB db;

    public OrderService() {
        this.db = DB.db;
    }

    public List<Order> getAllOrders() {
        return this.db.getAllOrders();
    }

    public ErrorType addOrder(final Order newOrder) {
        if (newOrder == null) {
            return ErrorType.INVALID_PARAMETER;
        }

        return this.db.addOrder(newOrder);

        //return newOrder;
    }

    public Order getOrder(int id){
        return this.db.getOrder(id);
    }

    public Order updateOrder(final int id, final Order updatedOrder) {
        final Order order = this.getOrder(id);

        if (order == null || updatedOrder == null){
            return null;
        }
        System.out.println("updating");
        //Optional.ofNullable(updatedBottle.getName()).ifPresent(d -> btl.setName(d));

        //if every update is valid
        if(true){
            this.db.updateStock(order, updatedOrder);
            Optional.ofNullable(updatedOrder.getPositions()).ifPresent(d -> order.setPositions(d));
            System.out.println("updated");
        }

        return order;
    }

    public void processOrder(final int id){
        final Order order = this.getOrder(id);
        if(order != null){
            order.setStatus(OrderStatus.PROCESSED);
        }
    }

    public void cancelOrder(final int id){
        final Order order = this.getOrder(id);
        if(order !=null){
            this.db.deleteOrder(id);
        }
    }

}
