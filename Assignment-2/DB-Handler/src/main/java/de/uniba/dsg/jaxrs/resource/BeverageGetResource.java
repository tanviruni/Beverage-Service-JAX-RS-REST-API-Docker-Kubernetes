package de.uniba.dsg.jaxrs.resource;

import de.uniba.dsg.jaxrs.controller.BeverageGetService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Logger;

@Path("beverage")
public class BeverageGetResource {


    private static final Logger logger = Logger.getLogger("BeverageGetResource");

    @GET
    @Path("bottles")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBottles(@Context final UriInfo uriInfo,
                               @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                               @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("Get all bottles. Pagination parameter: page-\" + page + \" pageLimit-\" + pageLimit");

        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeverageGetService.instance.getAllBottles() , uriInfo.getBaseUri())){
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

        List <Crate> crates = BeverageGetService.instance.getAllCrates();
        List<CrateDTO> dtos = CrateDTO.marshall(crates, uriInfo.getBaseUri());
        final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(dtos){
        };

        final Response build = Response.ok(entity).build();
        return build;

    }


    @GET
    @Path("bottles/{bottleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottleById( @PathParam("bottleId") final int bottleId) {
        logger.info("Get Bottle with Id: " + bottleId);
        final Bottle m = BeverageGetService.instance.getBottleById(bottleId);
        if (m == null) {
            logger.info("Bottle not found: " + bottleId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND, "There is no bottle with id "+bottleId+" in the database"))
                    .build();
        }
        return Response.ok(new BottleDTO(m)).build();
    }

    @GET
    @Path("crate/{crateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrateById(@PathParam("crateId") final int crateId) {
        logger.info("Get Crate with Id: " + crateId);
        final Crate m = BeverageGetService.instance.getCrateById(crateId);
        if (m == null){
            logger.info("Crate not found" + crateId );
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND, "There is no crate with id "+crateId+" in the database"))
                    .build();
        }
        return Response.ok(new CrateDTO(m)).build();
    }

}
