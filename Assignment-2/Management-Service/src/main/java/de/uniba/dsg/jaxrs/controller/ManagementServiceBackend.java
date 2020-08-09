package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.Configuration;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.BottleUpdateDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.dto.CrateUpdateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;
import de.uniba.dsg.jaxrs.resource.ManagementResource;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ManagementServiceBackend {

    private static final Logger log = Logger.getLogger("ManagementServiceBackend");
    private final String uri;

    public ManagementServiceBackend(String uri) {


        if ("".equals(uri)) {
            this.uri = "http://localhost:9999/v1";
        } else
            this.uri = uri;
        log.info("Using rest backend implementation with " + this.uri);
    }

    public List<Bottle> getBottles() throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/bottles") // http://localhost:9999/v1/beverage/bottles
                .request(MediaType.APPLICATION_JSON)
                .get();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<BottleDTO>>() {
            })
                    .stream()
                    .map(x -> x.unmarshall())
                    .collect(Collectors.toList());
        } else {
            log.info("Error in fetching all bottles from DB-Handler Status code " + response.getStatus());
        }
        //System.out.println(returnCat.toString());
        List<Bottle> list = new ArrayList<>();
        return list;
    }

    public Bottle getBottle(final int bottleId) throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/bottles/" + bottleId + "") // http://localhost:9999/v1/beverage/bottles/bottleId
                .request(MediaType.APPLICATION_JSON)
                .get();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<Bottle>() {
            });
        } else {
            log.info("Error in  bottle from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }

    public List<Crate> getCrates() throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/crates") // http://localhost:9999/v1/beverage/crates
                .request(MediaType.APPLICATION_JSON)
                .get();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<CrateDTO>>() {
            })
                    .stream()
                    .map(x -> {
                        return x.unmarshall();
                    })
                    .collect(Collectors.toList());

        } else {
            log.info("Error in fetching all crates from DB-Handler Status code " + response.getStatus());
        }
        //System.out.println(returnCat.toString());
        List<Crate> list = new ArrayList<>();
        return list;
    }


    public Crate getCrate(final int crateId) throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/crate/" + crateId + "") // http://localhost:9999/v1/beverage/crate/bottleId
                .request(MediaType.APPLICATION_JSON)
                .get();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<Crate>() {
            });
        } else {
            log.info("Error in fetching  crate from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }


    public Bottle addBottle(final BottleDTO bottleDTO) throws ProcessingException {

        if (bottleDTO == null) {
            //  return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Entity<BottleDTO> entity = Entity.json(bottleDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/addBottle") // http://localhost:9999/v1/beverage/addBottle
                .request(MediaType.APPLICATION_JSON)
                .post(entity);

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<Bottle>() {
            });
        } else {
            log.info("Error in adding bottle from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }

    public Crate addCrate(final CrateDTO crateDTO) throws ProcessingException {

        if (crateDTO == null) {
            //  return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Entity<CrateDTO> entity = Entity.json(crateDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/addCrate") // http://localhost:9999/v1/beverage/addBottle
                .request(MediaType.APPLICATION_JSON)
                .post(entity);

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<Crate>() {
            });
        } else {
            log.info("Error in adding crate from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }

    public Crate removeCrate(final int crateId) throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/deleteCrate/" + crateId + "") // http://localhost:9999/v1/beverage/deleteCrate/
                .request(MediaType.APPLICATION_JSON)
                .delete();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<Crate>() {
            });
        } else {
            log.info("Error in deleting crate from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }

    public Crate removeBottle(final int bottleId) throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/deleteBottle/" + bottleId + "") // http://localhost:9999/v1/beverage/deleteBottle/
                .request(MediaType.APPLICATION_JSON)
                .delete();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<Crate>() {
            });
        } else {
            log.info("Error in deleting bottle from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }

    public Response updateCrate(final int crateId, final CrateUpdateDTO crateUpdateDTO) throws ProcessingException {

        if (crateUpdateDTO == null) {
            //  return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Entity<CrateUpdateDTO> entity = Entity.json(crateUpdateDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/editCrate/" + crateId + "") // http://localhost:9999/v1/beverage/editBottle
                .request(MediaType.APPLICATION_JSON)
                .put(entity);

        if (response.getStatus() == 200) {
            return Response.ok().entity("Successfully updated").build();
        } else {
            log.info("Error in updating crate from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }


    public Response updateBottle(final int bottleId, final BottleUpdateDTO bottleUpdateDTO) throws ProcessingException {

        if (bottleUpdateDTO == null) {
            //  return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Entity<BottleUpdateDTO> entity = Entity.json(bottleUpdateDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/editBottle/" + bottleId + "") // http://localhost:9999/v1/beverage/editBottle
                .request(MediaType.APPLICATION_JSON)
                .put(entity);

        if (response.getStatus() == 200) {
            return Response.ok().entity("Successfully updated").build();
        } else {
            log.info("Error in updating bottle from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }

}
