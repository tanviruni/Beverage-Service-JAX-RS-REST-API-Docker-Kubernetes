package de.uniba.dsg.jaxrs.db;

import de.uniba.dsg.jaxrs.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DB {
    public static final DB db = new DB();

    private final List<Bottle> bottles;
    private final List<Crate> crates;
    private final List<Order> orders;

    public DB() {
        this.bottles = initBottles();
        this.crates = initCases();
        this.orders = initOrder();
    }

    private List<Bottle> initBottles() {
        return new ArrayList<>(Arrays.asList(
                new Bottle(1, "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34),
                new Bottle(2, "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17),
                new Bottle(3, "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11),
                new Bottle(4, "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5),
                new Bottle(5, "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3),
                new Bottle(6, "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66),
                new Bottle(7, "Premium Tafelwasser", 0.7, false, 0.0, 4.29, "Franken Brunnen", 12),
                new Bottle(8, "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57),
                new Bottle(9, "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42),
                new Bottle(10, "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12),
                new Bottle(11, "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34),
                new Bottle(12, "Cola", 1.5, false, 0.0, 1.79, "CCC", 69),
                new Bottle(13, "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12),
                new Bottle(14, "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25),
                new Bottle(15, "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55),
                new Bottle(16, "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8)
        ));
    }

    private List<Crate> initCases() {
        return new ArrayList<>(Arrays.asList(
                new Crate(1, this.bottles.get(0), 20, 14.99, 3),
                new Crate(2, this.bottles.get(1), 20, 15.99, 5),
                new Crate(3, this.bottles.get(2), 6, 30.00, 7),
                new Crate(4, this.bottles.get(7), 12, 1.99, 11),
                new Crate(5, this.bottles.get(8), 20, 11.99, 13),
                new Crate(6, this.bottles.get(11), 6, 10.99, 4),
                new Crate(7, this.bottles.get(12), 6, 11.99, 5),
                new Crate(8, this.bottles.get(13), 20, 35.00, 7),
                new Crate(9, this.bottles.get(14), 12, 20.00, 9)
        ));
    }

    private List<Order> initOrder() {
        return new ArrayList<>(Arrays.asList(
                new Order(1, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(3), 2),
                        new OrderItem(20, this.crates.get(3), 1),
                        new OrderItem(30, this.bottles.get(15), 1)
                )), 32.56, OrderStatus.SUBMITTED),
                new Order(2, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(13), 2),
                        new OrderItem(20, this.bottles.get(14), 2),
                        new OrderItem(30, this.crates.get(0), 1)
                )), 22.95, OrderStatus.PROCESSED),
                new Order(3, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(2), 1)
                )), 5.79, OrderStatus.SUBMITTED)
        ));
    }


    public List<Bottle> getAllBottles() {
        return this.bottles;
    }


    public void addBottleToDb(final Bottle m) {
        m.setId(this.bottles.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        System.out.println(m);
        this.bottles.add(m);
    }

    public Bottle getBottle(String name){
        for(Bottle b: this.bottles)
            if(b.getName().equalsIgnoreCase(name))
                return b;
        return null;
    }

    public List<Order> getAllOrders(){ return this.orders; }

    /***
     * return codes:
     *      2501        - invalid parameters. beverage not found
     *      2502        - insufficient stock
     *      2505        - successfully inserted
     ***/
    public ErrorType addOrder(final Order order) {
        order.setOrderId(this.orders.stream().map(Order::getOrderId).max(Comparator.naturalOrder()).orElse(0) + 1);
        order.setPrice();
        order.setStatus(OrderStatus.SUBMITTED);

        /**
         * stock validation
         */
        int returnVal = 0;
        for(OrderItem item: order.getPositions()){
            if(item.getBeverage().getClass()==Bottle.class){
                if(this.getBottle(item.getBeverage().getId())==null)
                    return ErrorType.ITEM_NOT_FOUND;  //beverage not found
                if(this.getBottle(item.getBeverage().getId()).getInStock() < item.getQuantity())
                    return ErrorType.INSUFFICIENT_STOCK; //insufficient stock
            }
            else {
                if(this.getCrate(item.getBeverage().getId())==null)
                    return ErrorType.ITEM_NOT_FOUND;  //beverage not found
                if(this.getCrate(item.getBeverage().getId()).getInStock() < item.getQuantity())
                    return ErrorType.INSUFFICIENT_STOCK; //insufficient stock
            }
        }



        this.orders.add(order);
        this.adjustStock(order, 1);

        return ErrorType.INSERT_SUCCESSFUL;
    }

    private void adjustStock(Order order, int action){
        for(OrderItem item: order.getPositions()){
            if(item.getBeverage().getType()==BeverageType.BOTTLE_TYPE){
                Bottle b = this.getBottle(item.getBeverage().getId());
                int prev = b.getInStock();
                if(action==1)
                    b.setInStock(prev-item.getQuantity());
                else
                    b.setInStock(prev+item.getQuantity());
            }
            else{
                Crate c = this.getCrate(item.getBeverage().getId());
                int prev = c.getInStock();
                if(action==1)
                    c.setInStock(prev-item.getQuantity());
                else
                    c.setInStock(prev+item.getQuantity());
            }

        }
    }

    public void updateStock(Order oldOrder, Order newOrder){
        this.adjustStock(oldOrder, 0);
        this.adjustStock(newOrder, 1);

    }
    public Order getOrder(final int id) {
        for(Order order: this.orders)
            if(order.getOrderId() == id)
                return order;
        return null;
    }

    public void deleteOrder(final int id){
        Order order = this.getOrder(id);
        this.orders.remove(order);
    }

    public Bottle getBottle(final int id) {
        return this.bottles.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Bottle getBottleById(final int bottleId) {
        return this.bottles.stream().filter(m -> m.getId() == bottleId).findFirst().orElse(null);
    }

    public List<Crate> getAllCrates() {
        return this.crates;
    }

    public void addCrateToDb(final Crate m) {
        m.setId(this.crates.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        System.out.println(m);
        this.crates.add(m);
    }

    public Crate getCrate(final int id) {
        return this.crates.stream().filter(m -> m.getId() ==id).findFirst().orElse(null);
    }

    public Crate getCrateById(final int crateId) {
        return this.crates.stream().filter(m -> m.getId() == crateId).findFirst().orElse(null);
    }
}
