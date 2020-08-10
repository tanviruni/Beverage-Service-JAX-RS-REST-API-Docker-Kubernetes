package de.uniba.dsg.jaxrs.resource;


import de.uniba.dsg.jaxrs.Configuration;
import de.uniba.dsg.jaxrs.controller.ManagementServiceBackend;
import de.uniba.dsg.jaxrs.dto.*;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;

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
    public Response getBeverages( ) {
        logger.info("Getting all beverages.");


        try {
            List<Bottle> bottles = ManagementServiceBackend.instance.getBottles();
            List<Crate> crates = ManagementServiceBackend.instance.getCrates();
            BeveragesDTO dtos = new BeveragesDTO(bottles, crates);
            return Response.ok(dtos).build();
        } catch (ProcessingException e) {
            logger.warning("DB-Handler is not reachable");
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }
    }

    @GET
    @Path("getBottle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottle(@QueryParam("bottleId") final int bottleId) {
        logger.info("Getting  bottle. bottleId-"+bottleId);

        try {
            Bottle bottle = ManagementServiceBackend.instance.getBottle(bottleId);
            if (bottle == null) {
                logger.info("Bottle not found: bottleId" + bottleId);
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND, "There is no bottle with id "+bottleId+" in the database"))
                        .build();
            }
            logger.info(bottle.toString());
            return Response.ok().entity(new BottleDTO(bottle, null)).build();
        } catch (ProcessingException e) {
            logger.warning("DB-Handler is not reachable");
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }

        //return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("getCrate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrate(@QueryParam("crateId") final int crateId) {
        logger.info("Getting  crate. crateId-"+crateId);

        try {
            Crate crate = ManagementServiceBackend.instance.getCrate(crateId);
            if (crate==null){
                logger.info("Crate not found: crateId" + crateId);
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND, "There is no crate with id "+crateId+" in the database"))
                        .build();
            }

            return Response.ok().entity(new CrateDTO(crate, null)).build();
        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }
    }

    @POST
    @Path("addBottle")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(final BottleCreateDTO bottleDTO) {
        logger.info("Create a bottle in DB: " + bottleDTO);

        if (bottleDTO == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }


        ManagementServiceBackend.instance.addBottle(bottleDTO);


        Response build = Response.ok().entity("Bottle successfully inserted").build();
        return build;

    }

    @POST
    @Path("addCrate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCrate(final CrateCreateDTO crateDTO, @Context final UriInfo uriInfo) {
        logger.info("Create a crate in DB: " + crateDTO);

        if (crateDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }


        ManagementServiceBackend.instance.addCrate(crateDTO);


        Response build = Response.ok().entity("Crate successfully added").build();
        return build;

    }


    @DELETE
    @Path("removeCrate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCrate(@QueryParam("crateId") final int crateId) {
        logger.info("Removing  crate. crateId - "+crateId);

        try {
            int status = ManagementServiceBackend.instance.removeCrate(crateId);
            switch (status){
                case 200:
                    logger.info("Crate successfully removed");
                    return Response.ok().entity("Crate successfully removed").build();
                case 404:
                    logger.warning("Crate with id "+crateId+" not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Crate with id "+crateId+" not found")).build();
            }

        } catch (ProcessingException e) {
            logger.warning("DB-Handler is not reachable");
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }

        return null;
    }


    @DELETE
    @Path("removeBottle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeBottle( @QueryParam("bottleId") final int bottleId) {
        logger.info("Removing bottle bottleId-"+bottleId);

        try {
            int status = ManagementServiceBackend.instance.removeBottle(bottleId);
            switch (status){
                case 200:
                    logger.info("Bottle deleted");
                    return Response.ok("Bottle deleted").build();
                case 403:
                    logger.warning("Bottle with ID "+bottleId+" is used in crate/crates");
                    return Response
                            .status(Response.Status.FORBIDDEN)
                            .entity(new ErrorMessage(ErrorType.LINKED_WITH_CRATES, "Bottle with ID "+bottleId+" is used in crate/crates"))
                            .build();
                case 404:
                    logger.warning("Bottle with id "+bottleId+" not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Bottle with id "+bottleId+" not found")).build();
                default:
                    logger.warning("Error deleting bottle. status code "+status);
            }

        } catch (ProcessingException e) {
            return Response.status(400).entity("DB-Handler is not reachable").build();
        }
        return null;
    }


    @PUT
    @Path("updateCrate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCrate(@QueryParam("carateId") final int crateId, final CrateUpdateDTO crateUpdateDTO, @Context final UriInfo uriInfo) {
        logger.info("Updating crate crateId-" + crateId);

        if (crateUpdateDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }


        int status = ManagementServiceBackend.instance.updateCrate(crateId,crateUpdateDTO);
        switch (status){
            case 406:
                logger.warning("Bottle with id "+crateUpdateDTO.getBottleId()+" not found");
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity((new ErrorMessage(ErrorType.ITEM_NOT_FOUND, "Bottle with id "+crateUpdateDTO.getBottleId()+" not found"))).build();
            case 404:
                logger.warning("Crate with id "+crateId+" not found");
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Crate with id "+crateId+" not found")).build();
            case 200:
                logger.info("Crate updated");
                return Response.ok().entity("Successfully updated").build();
            default:
                logger.warning("Error deleting bottle. status code "+status);
                return null;
        }

    }

    @PUT
    @Path("updatebottle")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBottle(@QueryParam("bottleId") final int bottleId, final BottleUpdateDTO bottleUpdateDTO) {
        logger.info("Updating bottle bottleId-" + bottleId);

        if (bottleUpdateDTO == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }


        int status = ManagementServiceBackend.instance.updateBottle(bottleId,bottleUpdateDTO);
        switch (status){
            case 404:
                logger.info("Bottle not found");
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Bottle with id "+bottleId+" not found")).build();
            case 200:
                logger.info("Bottle updated");
                return Response.ok().entity("Successfully updated").build();

        }


        Response build = Response.ok().build();
        return build;

    }
}
