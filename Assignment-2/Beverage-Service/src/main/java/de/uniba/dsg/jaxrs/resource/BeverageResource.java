package de.uniba.dsg.jaxrs.resource;


import de.uniba.dsg.jaxrs.Configuration;
import de.uniba.dsg.jaxrs.controller.BeverageServiceBackend;
import de.uniba.dsg.jaxrs.dto.BeveragesDTO;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@Path("beverage")
public class BeverageResource {


    private static final Logger logger = Logger.getLogger("BeverageResource");


    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeverages(@Context final UriInfo uriInfo,
                                     @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                     @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all beverages. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        BeverageServiceBackend backend = new BeverageServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));
        try{
            List<Bottle> bottles = backend.getBottles();
            List<Crate> crates = backend.getCrates();
            BeveragesDTO dtos = new BeveragesDTO(bottles, crates);
            return Response.ok(dtos).build();
        }catch (ProcessingException e){
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }

        //return Response.status(Response.Status.BAD_REQUEST).build();
    }



}
