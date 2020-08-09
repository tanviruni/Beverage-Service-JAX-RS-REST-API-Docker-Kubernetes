package de.uniba.dsg.jaxrs.resource;


import de.uniba.dsg.jaxrs.Configuration;
import de.uniba.dsg.jaxrs.controller.ManagementServiceBackend;
import de.uniba.dsg.jaxrs.dto.*;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@Path("management")
public class ManagementResource {


    private static final Logger logger = Logger.getLogger("ManagementResource");


    @GET
    @Path("allBeverages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeverages(@Context final UriInfo uriInfo,
                                 @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                 @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all beverages. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));
        try {
            List<Bottle> bottles = backend.getBottles();
            List<Crate> crates = backend.getCrates();
            BeveragesDTO dtos = new BeveragesDTO(bottles, crates);
            return Response.ok(dtos).build();
        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }

        //return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("getBottle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottle(@Context final UriInfo uriInfo,
                              @QueryParam("bottleId") final int bottleId,
                              @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                              @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get  bottle. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));
        try {
            Bottle bottle = backend.getBottle(bottleId);
            return Response.ok().entity(new BottleDTO(bottle, null)).build();
        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }

        //return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("getCrate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrate(@Context final UriInfo uriInfo,
                             @QueryParam("crateId") final int crateId,
                             @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                             @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get  crate. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));
        try {
            Crate crate = backend.getCrate(crateId);
            return Response.ok().entity(new CrateDTO(crate, null)).build();
        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }

        //return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("addBottle")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(final BottleDTO bottleDTO, @Context final UriInfo uriInfo) {
        logger.info("Create a bottle in DB: " + bottleDTO);

        if (bottleDTO == null) {
            //    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));

        final Bottle btl = backend.addBottle(bottleDTO);


        Response build = Response.ok(btl).build();
        return build;

    }

    @POST
    @Path("addCrate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCrate(final CrateDTO crateDTO, @Context final UriInfo uriInfo) {
        logger.info("Create a crate in DB: " + crateDTO);

        if (crateDTO == null) {
            //    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));

        final Crate btl = backend.addCrate(crateDTO);


        Response build = Response.ok(btl).build();
        return build;

    }


    @DELETE
    @Path("removeCrate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCrate(@Context final UriInfo uriInfo,
                             @QueryParam("crateId") final int crateId,
                             @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                             @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get  crate. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));
        try {
             backend.removeCrate(crateId);
            return Response.ok().build();
        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }
    }


    @DELETE
    @Path("removeBottle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeBottle(@Context final UriInfo uriInfo,
                                @QueryParam("bottleId") final int bottleId,
                                @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get  crate. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));
        try {
            backend.removeBottle(bottleId);
            return Response.ok().build();
        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }
    }


    @PUT
    @Path("updateCrate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCrate(@QueryParam("carateId") final int carateId, final CrateUpdateDTO crateUpdateDTO, @Context final UriInfo uriInfo) {
        logger.info("Update a crate in DB: " + crateUpdateDTO);

        if (crateUpdateDTO == null) {
            //    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));

         backend.updateCrate(carateId,crateUpdateDTO);


        Response build = Response.ok().build();
        return build;

    }

    @PUT
    @Path("updatebottle")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBottle(@QueryParam("bottleId") final int bottleId, final BottleUpdateDTO bottleUpdateDTO, @Context final UriInfo uriInfo) {
        logger.info("Update a bottle in DB: " + bottleUpdateDTO);

        if (bottleUpdateDTO == null) {
            //    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        ManagementServiceBackend backend = new ManagementServiceBackend(Configuration.loadProperties().getProperty("remoteUri"));

       backend.updateBottle(bottleId,bottleUpdateDTO);


        Response build = Response.ok().build();
        return build;

    }
}
