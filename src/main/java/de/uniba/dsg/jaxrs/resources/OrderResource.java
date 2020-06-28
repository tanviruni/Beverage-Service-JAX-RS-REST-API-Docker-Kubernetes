package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.controller.OrderService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.dto.OrderDTO;
import de.uniba.dsg.jaxrs.dto.OrderUpdateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;
import de.uniba.dsg.jaxrs.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@Path("orders")
public class OrderResource {

    private static final Logger logger = Logger.getLogger("OrderResource");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(){
        final GenericEntity<List<OrderDTO>> entity = new GenericEntity<List<OrderDTO>>(OrderDTO.marshall(OrderService.instance.getAllOrders() )){
        };

        Response build = Response.ok(entity).build();
        //Response build = Response.ok(BeverageService.instance.getAllBottles()).build();
        return build;
    }

    @GET
    @Path("getOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@QueryParam("id") final int id){
        final GenericEntity<OrderDTO> entity = new GenericEntity<OrderDTO>(new OrderDTO(OrderService.instance.getOrder(id))){};
        System.out.println();
        return Response.ok(entity).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(final OrderDTO orderDTO){
        if (orderDTO == null) {
            //***TBE
            //return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        final Order newOrder = orderDTO.unmarshall();

        int res = OrderService.instance.addOrder(newOrder);

        //String
        //switch (res)

        return Response.status(Response.Status.OK).entity("This is my personalized message").build();
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
        }

        final Order resultord = OrderService.instance.updateOrder(id, updatedOrder.unmarshall());
        System.out.println("udating resource");
        return Response.ok().entity(new OrderDTO(resultord)).build();
        //return Response.ok().build();
    }
}
