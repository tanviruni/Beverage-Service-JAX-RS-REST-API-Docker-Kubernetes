package de.uniba.dsg.jaxrs.resources;


import de.uniba.dsg.jaxrs.controller.BeveageService;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("beverage")
public class BeverageResource {

    @GET
    @Path("{bottles}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBottles() {

        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeveageService.instance.getAllBottles() )){
        };

         Response build = Response.ok(entity).build();
        //Response build = Response.ok(BeveageService.instance.getAllBottles()).build();
        return build;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(final BottleDTO bottleDTO) {

        final Bottle newBottle = bottleDTO.unmarshall();
        // generate id and other stuff
        final Bottle movie = BeveageService.instance.addBottle(newBottle);


        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeveageService.instance.getAllBottles() )){
        };


        return Response.ok(entity).build();

    }

    @PUT
    @Path("{editBottle}/{bottle-id}")
    public Response editBottle(@PathParam("bottle-id") final int id, final BottleUpdateDTO updatedBottle) {

        final Bottle bl = BeveageService.instance.getBottle(id);

        if (bl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Bottle resultbl = BeveageService.instance.updateBottle(id, updatedBottle.unmarshall());

        final GenericEntity<List<BottleDTO>> entity = new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(BeveageService.instance.getAllBottles() )){
        };

        Response build = Response.ok(entity).build();
        return build;
    }



}

/////working