package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.controller.OrderService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.dto.OrderDTO;
import de.uniba.dsg.jaxrs.dto.OrderUpdateDTO;
import de.uniba.dsg.jaxrs.model.*;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;
import de.uniba.dsg.jaxrs.model.Order;
import de.uniba.dsg.jaxrs.model.api.PaginatedOrders;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@Path("orders")
public class OrderResource {

    private static final Logger logger = Logger.getLogger("OrderResource");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@Context final UriInfo uriInfo,
                              @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                              @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all orders. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        // Parameter validation
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Order> helper = new PaginationHelper<>(OrderService.instance.getAllOrders());
        final PaginatedOrders response = new PaginatedOrders(helper.getPagination(uriInfo, page, pageLimit), OrderDTO.marshall(helper.getPaginatedList()), uriInfo.getRequestUri());

        return Response.ok(response).build();

        /*final GenericEntity<List<OrderDTO>> entity = new GenericEntity<List<OrderDTO>>(OrderDTO.marshall(OrderService.instance.getAllOrders() )){
        };

        Response build = Response.ok(entity).build();
        //Response build = Response.ok(BeverageService.instance.getAllBottles()).build();
        return build;*/
    }

    @GET
            @Path("getOrder")
            @Produces(MediaType.APPLICATION_JSON)
            public Response getOrder(@QueryParam("id") final int id) {
                final GenericEntity<OrderDTO> entity = new GenericEntity<OrderDTO>(new OrderDTO(OrderService.instance.getOrder(id))) {
                };
                System.out.println();
                return Response.ok(entity).build();
            }

            @POST
            @Consumes(MediaType.APPLICATION_JSON)
            public Response createOrder(final OrderDTO orderDTO) {
                if (orderDTO == null) {
                    //***TBE
                    //return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
                }
                final Order newOrder = orderDTO.unmarshall();

                ErrorType res;
                for (OrderItem item : newOrder.getPositions()) {
                    if (item.getBeverage() == null)
                        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND)).build();
                }
                res = OrderService.instance.addOrder(newOrder);


                switch (res) {
                    case INSERT_SUCCESSFUL:
                        return Response.status(Response.Status.OK).entity(new ErrorMessage(ErrorType.INSERT_SUCCESSFUL)).build();

                    case INSUFFICIENT_STOCK:
                        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(ErrorType.INSUFFICIENT_STOCK)).build();

                    case ITEM_NOT_FOUND:
                        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND)).build();

                    default:
                        return Response.status(Response.Status.BAD_REQUEST).build();
                }


//
//        return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(CatResource.class).path(CatResource.class, "getCat").build(cat.getId())).build();
            }

            @PUT
            @Path("editOrder/{id}")
            public Response editOrder(@PathParam("id") final int id, final OrderUpdateDTO updatedOrder) {
                logger.info("Updating order " + updatedOrder);
                if (updatedOrder == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
                }

                final Order ord = OrderService.instance.getOrder(id);


                if (ord == null) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                } else if (ord.getStatus() == OrderStatus.PROCESSED)
                    return Response.status(Response.Status.UNAUTHORIZED).entity("This order is already processed").build();

                final Order resultord = OrderService.instance.updateOrder(id, updatedOrder.unmarshall());
                System.out.println("udating resource");
                return Response.ok().entity(new OrderDTO(resultord)).build();
                //return Response.ok().build();
            }

            @GET
            @Path("process/{id}")
            public Response processOrder(@PathParam("id") final int id) {
                //logger.info("Updating order " + updatedOrder);

                final Order ord = OrderService.instance.getOrder(id);

                if (ord == null) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                } else if (ord.getStatus() == OrderStatus.PROCESSED)
                    return Response.status(Response.Status.UNAUTHORIZED).entity("This order is already processed").build();

                OrderService.instance.processOrder(id);
                logger.info("Order with id - " + id + " has been processed");
                return Response.ok().entity("Order with id - " + id + " has been processed").build();

            }

            @GET
            @Path("cancelOrder/{id}")
            public Response cancelOrder(@PathParam("id") final int id) {
                final Order ord = OrderService.instance.getOrder(id);

                if (ord == null) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                } else if (ord.getStatus() == OrderStatus.PROCESSED)
                    return Response.status(Response.Status.UNAUTHORIZED).entity("This order is already processed").build();

                OrderService.instance.cancelOrder(id);
                logger.info("Order with id - " + id + " has been cancelled");
                return Response.ok().entity("Order with id - " + id + " has been cancelled").build();
            }

        }


