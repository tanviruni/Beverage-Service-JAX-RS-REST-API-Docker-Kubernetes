package de.uniba.dsg.jaxrs.resources;


import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.dto.CrateUpdateDTO;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("beverage")
public class BeverageResource {

    @GET
    @Path("{bottles}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBottles() {

        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeverageService.instance.getAllBottles() )){
        };

         Response build = Response.ok(entity).build();
        //Response build = Response.ok(BeverageService.instance.getAllBottles()).build();
        return build;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(final BottleDTO bottleDTO) {

        final Bottle newBottle = bottleDTO.unmarshall();
        // generate id and other stuff
        final Bottle movie = BeverageService.instance.addBottle(newBottle);


        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeverageService.instance.getAllBottles() )){
        };

        Response build = Response.ok(entity).build();
        return build;

    }

    @PUT
    @Path("{editBottle}/{bottle-id}")
    public Response editBottle(@PathParam("bottle-id") final int id, final BottleUpdateDTO updatedBottle) {

        final Bottle bl = BeverageService.instance.getBottle(id);

        if (bl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Bottle resultbl = BeverageService.instance.updateBottle(id, updatedBottle.unmarshall());

        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeverageService.instance.getAllBottles() )){
        };

        Response build = Response.ok(entity).build();
        return build;
    }

    @GET
    @Path("crates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrates() {

        final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates())) {
        };

        Response build = Response.ok(entity).build();
        return build;
    }

    @POST
    @Path("crates")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCrate(final CrateDTO crateDTO) {
        final Crate newCrate = crateDTO.unmarshall();

        final Crate create = BeverageService.instance.addCrate(newCrate);

        final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates())) {
        };

        Response build = Response.ok(entity).build();
        return build;
    }

    @PUT
    @Path("editCrate/{crate-id}")
    public Response editCrate(@PathParam("crate-id") final int id, final CrateUpdateDTO updatedCrate) {

        final Crate cr = BeverageService.instance.getCrate(id);

        if (cr == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Crate resultCr = BeverageService.instance.updateCrate(id, updatedCrate.unmarshall());

        final GenericEntity<List<CrateDTO>> entity = new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(BeverageService.instance.getAllCrates() )){
        };

        Response build = Response.ok(entity).build();
        return build;

    }


}
