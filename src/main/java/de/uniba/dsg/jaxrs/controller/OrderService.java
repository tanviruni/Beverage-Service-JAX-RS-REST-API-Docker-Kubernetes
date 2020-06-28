package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Order;

import java.util.List;

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

}
