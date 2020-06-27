package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BeveageService;
import de.uniba.dsg.jaxrs.controller.OrderService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.OrderDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("orders")
public class OrderResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(){
        final GenericEntity<List<OrderDTO>> entity = new GenericEntity<List<OrderDTO>>(OrderDTO.marshall(OrderService.instance.getAllOrders() )){
        };

        Response build = Response.ok(entity).build();
        //Response build = Response.ok(BeveageService.instance.getAllBottles()).build();
        return build;
    }
}
