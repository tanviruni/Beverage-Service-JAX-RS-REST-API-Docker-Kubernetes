package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Order;

import java.util.List;
import java.util.Optional;

public class OrderService {
    public static final OrderService instance = new OrderService();

    private final DB db;

    public OrderService() {
        this.db = new DB();
    }

    public List<Order> getAllOrders() {
        return this.db.getAllOrders();
    }

    public Order addOrder(final Order newOrder) {
        if (newOrder == null) {
            return null;
        }

        this.db.addOrder(newOrder);

        return newOrder;
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
            Optional.ofNullable(updatedOrder.getPositions()).ifPresent(d -> order.setPositions(d));
            System.out.println("updated");
        }

        return order;
    }


}
