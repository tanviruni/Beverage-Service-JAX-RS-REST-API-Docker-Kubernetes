package de.uniba.dsg.jaxrs.resources;


import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.dto.CrateUpdateDTO;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
@Path("beverage")
public class BeverageResource {

    private static final Logger logger = Logger.getLogger("BeverageResource");

    @GET
    @Path("{bottles}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBottles(@Context final UriInfo uriInfo) {
        logger.info("Get all bottles");
        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeverageService.instance.getAllBottles() , uriInfo.getBaseUri())){
        };

         Response build = Response.ok(entity).build();
        //Response build = Response.ok(BeverageService.instance.getAllBottles()).build();
        return build;
    }


    @GET
    @Path("{bottle}/{bottleId}")
    public Response getBottleById( @PathParam("bottleId") final int bottleId, @Context final UriInfo uriInfo) {
        logger.info("Get Bottle with Id: " + bottleId);
        final Bottle m = BeverageService.instance.getBottleById(bottleId);
        if (m == null) {
            logger.info("Bottle not found: " + bottleId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new BottleDTO(m, uriInfo.getBaseUri())).build();
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
        final Bottle btl = BeverageService.instance.addBottle(newBottle);
        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path(BeverageResource.class).path(BeverageResource.class, "getBottleById").build("bottle?bottleId=",btl.getId());
        logger.info("created uri - "+uri.getPath());


        return Response.created(uri).build();


    }

    @PUT
    @Path("{editBottle}/{bottle-id}")
    public Response editBottle(@PathParam("bottle-id") final int id, final BottleUpdateDTO updatedBottle, @Context final UriInfo uriInfo) {
        logger.info("Update bottle " + updatedBottle);
        if (updatedBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Bottle bl = BeverageService.instance.getBottle(id);

        if (bl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Bottle resultbl = BeverageService.instance.updateBottle(id, updatedBottle.unmarshall());

        return Response.ok().entity(new BottleDTO(resultbl,uriInfo.getBaseUri())).build();
    }

    @GET
    @Path("crates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrates(@Context final UriInfo uriInfo){
        logger.info("Get all crates");

        final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates(), uriInfo.getBaseUri())){
        };

        final Response build = Response.ok(entity).build();
        return build;
    }


    @GET
    @Path("crates/{crateId}")
    public Response getCrateById(@PathParam("crateId") final int crateId, @Context final UriInfo uriInfo) {
        logger.info("Get Crate with Id: " + crateId);
        final Crate m = BeverageService.instance.getCrateById(crateId);
        if (m == null){
            logger.info("Crate not found" + crateId );
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new CrateDTO(m, uriInfo.getBaseUri())).build();
    }

    @POST
    @Path("addCrates")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCrate(final CrateDTO crateDTO, @Context final UriInfo uriInfo) {
        logger.info("Create a crate in DB: " + crateDTO);

        if(crateDTO == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Crate Body was empty")).build();
        }

        final Crate newCrate = crateDTO.unmarshall();

        final Crate crate = BeverageService.instance.addCrate(newCrate);
        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path(BeverageResource.class).path(BeverageResource.class, "getCrateById").build(crate.getId());
        logger.info("created uri for crate - " +uri.getPath());

        return Response.created(uri).build();

        /*final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates())) {
        };

        Response build = Response.ok(entity).build();
        return build;*/
    }

    @PUT
    @Path("editCrate/{crate-id}")
    public Response editCrate(@PathParam("crate-id") final int id, final CrateUpdateDTO updatedCrate, @Context final UriInfo uriInfo) {
        logger.info("Update crate " + updatedCrate);
        if(updatedCrate == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Crate body was empty")).build();
        }

        final Crate cr = BeverageService.instance.getCrate(id);

        if (cr == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Crate resultCr = BeverageService.instance.updateCrate(id, updatedCrate.unmarshall());

        return Response.ok().entity(new CrateDTO(resultCr,uriInfo.getBaseUri())).build();

//        /*final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates() )){
//        };*/
//
//        Response build = Response.ok(entity).build();
//        return build;

    }


}
