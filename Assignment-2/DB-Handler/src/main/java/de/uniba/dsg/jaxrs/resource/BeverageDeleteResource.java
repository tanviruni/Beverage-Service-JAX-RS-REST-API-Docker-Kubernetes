package de.uniba.dsg.jaxrs.resource;

import de.uniba.dsg.jaxrs.controller.BeverageDeleteService;
import de.uniba.dsg.jaxrs.controller.BeverageGetService;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

@Path("beverage")
public class BeverageDeleteResource {
    private static final Logger logger = Logger.getLogger("BeverageDeleteResource");

    @DELETE
    @Path("deleteBottle/{bottleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBottle(@PathParam("bottleId") final int bottleId){
        Bottle bottle = BeverageGetService.instance.getBottleById(bottleId);

        if(bottle == null) return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Bottle with id "+bottleId+" not found")).build();
        List<Crate> crates = BeverageDeleteService.instance.searchCratesWithBottle(bottle);
        if(crates.size() > 0){
            String list = "";
            for (Crate c: crates)
                list += c.getId() + " ";
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage(ErrorType.LINKED_WITH_CRATES, "Bottle with ID "+bottleId+" is used in the following crate/crates: "+list))
                    .build();
        }
        BeverageDeleteService.instance.deleteBottle(bottle);
        logger.info("Bottle with id "+bottleId+" deleted");
        return Response.ok("Bottle deleted").build();
    }

    @DELETE
    @Path("deleteCrate/{crateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCrate(@PathParam("crateId") final int crateId){
        Crate crate = BeverageGetService.instance.getCrateById(crateId);

        if(crate==null) return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Crate with id "+crateId+" not found")).build();
        BeverageDeleteService.instance.deleteCrate(crate);
        logger.info("Crate with id "+crateId+" deleted");
        return Response.ok("Crate deleted").build();
    }
}
