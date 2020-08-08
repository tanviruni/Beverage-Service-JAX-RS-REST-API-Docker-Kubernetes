package de.uniba.dsg.jaxrs.resource;

import de.uniba.dsg.jaxrs.controller.BeverageGetService;
import de.uniba.dsg.jaxrs.controller.BeveragePostService;
import de.uniba.dsg.jaxrs.dto.BottleCreateDTO;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.CrateCreateDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.logging.Logger;

@Path("beverage")
public class BeveragePostResource {
    private static final Logger logger = Logger.getLogger("BeveragePostResource");

    @POST
    @Path("addBottle")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(final BottleCreateDTO bottleDTO) {
        logger.info("Create a bottle in DB: " + bottleDTO);

        if (bottleDTO == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Bottle newBottle = bottleDTO.unmarshall();
        BeveragePostService.instance.addBottle(newBottle);

        logger.info("Bottle created");


        return Response.status(Response.Status.CREATED).entity("Successfully created").build();
    }

    @POST
    @Path("addCrate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCrate(final CrateCreateDTO crateDTO, @Context final UriInfo uriInfo) {
        logger.info("Create a crate in DB: " + crateDTO);

        if(crateDTO == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Crate Body was empty")).build();
        }

        final Bottle bottle = BeverageGetService.instance.getBottleById(crateDTO.getBottleId());
        final Crate newCrate = crateDTO.unmarshall(bottle);

        BeveragePostService.instance.addCrate(newCrate);

        logger.info("Crate created");

        return Response.status(Response.Status.CREATED).entity("Successfully created").build();
    }

}
