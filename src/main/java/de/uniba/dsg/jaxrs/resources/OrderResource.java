package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.controller.OrderService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.OrderDTO;
import de.uniba.dsg.jaxrs.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("orders")
public class OrderResource {

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

        OrderService.instance.addOrder(newOrder);

        return Response.ok().build();
//
//        return Response.created(UriBuilder.fromUri(uriInfo.getBaseUri()).path(CatResource.class).path(CatResource.class, "getCat").build(cat.getId())).build();
    }
}
