package de.uniba.dsg.jaxrs.resources;


import de.uniba.dsg.jaxrs.controller.BeveageService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
@Path("beverage")
public class BeverageResource {

    private static final Logger logger = Logger.getLogger("BeverageResource");

    @GET
    @Path("{bottles}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBottles() {
        logger.info("Get all bottles");
        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeveageService.instance.getAllBottles() )){
        };

         Response build = Response.ok(entity).build();
        return build;
    }


    @GET
    @Path("{bottle}/{bottleId}")
    public Response getBottleById( @PathParam("bottleId") final int bottleId) {
        logger.info("Get Movie with Id: " + bottleId);
        final Bottle m = BeveageService.instance.getBottleById(bottleId);
        if (m == null) {
            logger.info("Movie not found: " + bottleId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new BottleDTO(m)).build();
    }


    @POST
    @Path("{addBottle}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(final BottleDTO bottleDTO, @Context final UriInfo uriInfo) {
        logger.info("Create a bottle in DB: " + bottleDTO);

        if (bottleDTO == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Bottle newBottle = bottleDTO.unmarshall();
        // generate id and other stuff
        final Bottle btl = BeveageService.instance.addBottle(newBottle);
        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path(BeverageResource.class).path(BeverageResource.class, "getBottleById").build("bottle?bottleId=",btl.getId());
        logger.info("created uri - "+uri.getPath());


        return Response.created(uri).build();


    }

    @PUT
    @Path("{editBottle}/{bottle-id}")
    public Response editBottle(@PathParam("bottle-id") final int id, final BottleUpdateDTO updatedBottle) {
        logger.info("Update bottle " + updatedBottle);
        if (updatedBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Bottle bl = BeveageService.instance.getBottle(id);

        if (bl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Bottle resultbl = BeveageService.instance.updateBottle(id, updatedBottle.unmarshall());

        return Response.ok().entity(new BottleDTO(resultbl)).build();
    }



}
