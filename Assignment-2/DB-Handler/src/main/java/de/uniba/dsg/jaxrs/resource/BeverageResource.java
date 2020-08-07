package de.uniba.dsg.jaxrs.resource;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@Path("beverage")
public class BeverageResource {


    private static final Logger logger = Logger.getLogger("BeverageResource");

    @GET
    @Path("{bottles}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBottles(@Context final UriInfo uriInfo,
                               @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                               @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all bottles. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeverageService.instance.getAllBottles() , uriInfo.getBaseUri())){
        };

        Response build = Response.ok(entity).build();
        return build;
    }



    @GET
    @Path("crates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrates(@Context final UriInfo uriInfo,
                              @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                              @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all crates. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");


        final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates(), uriInfo.getBaseUri())){
        };

        final Response build = Response.ok(entity).build();
        return build;

    }





}
