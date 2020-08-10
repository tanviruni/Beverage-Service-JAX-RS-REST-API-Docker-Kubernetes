package de.uniba.dsg.jaxrs.resource;

import de.uniba.dsg.jaxrs.controller.BeverageGetService;
import de.uniba.dsg.jaxrs.controller.BeveragePutService;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.dto.CrateUpdateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.model.ErrorMessage;
import de.uniba.dsg.jaxrs.model.ErrorType;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("beverage")
public class BeveragePutResource {
    private static final Logger logger = Logger.getLogger("BeveragePutResource");

    @PUT
    @Path("editBottle/{bottle-id}")
    public Response editBottle(@PathParam("bottle-id") final int id, final BottleUpdateDTO updatedBottle) {

        if (updatedBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Bottle bl = BeverageGetService.instance.getBottleById(id);

        if (bl == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Bottle with id "+id+" not found")).build();
        }

        BeveragePutService.instance.updateBottle(updatedBottle.unmarshall(bl));
        logger.info("Bottle updated");

        return Response.ok().entity("Successfully updated").build();
    }

    @PUT
    @Path("editCrate/{crate-id}")
    public Response editCrate(@PathParam("crate-id") final int id, final CrateUpdateDTO updatedCrate) {

        if (updatedCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        final Crate cr = BeverageGetService.instance.getCrateById(id);
        Bottle bl = null;
        if(updatedCrate.getBottleId()!=null)   {
            bl = BeverageGetService.instance.getBottleById(updatedCrate.getBottleId().intValue());
            if(bl==null) return Response.status(Response.Status.NOT_ACCEPTABLE).entity((new ErrorMessage(ErrorType.ITEM_NOT_FOUND, "Bottle with id "+updatedCrate.getBottleId()+" not found"))).build();
        }


        if (cr == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.ITEM_NOT_FOUND,"Crate with id "+id+" not found")).build();
        }

        BeveragePutService.instance.updateCrate(updatedCrate.unmarshall(cr, bl));
        logger.info("Crate updated");

        return Response.ok().entity("Successfully updated").build();
    }
}
