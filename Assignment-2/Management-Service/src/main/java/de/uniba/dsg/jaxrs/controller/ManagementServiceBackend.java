package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.Configuration;
import de.uniba.dsg.jaxrs.dto.*;
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
    public static final ManagementServiceBackend instance = new ManagementServiceBackend(Configuration.getDBHandlerUri());

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

        switch (response.getStatus()){
            case 200:
                return response.readEntity(new GenericType<Bottle>() { });
            case 404:
                return null;
            default:
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

        }else if(response.getStatus() ==  404) {
            return null;
        }
        else {
            log.info("Error in fetching all crates from DB-Handler Status code " + response.getStatus());
        }

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
        }else if(response.getStatus()==404){
            return null;
        } else {
            log.info("Error in fetching  crate from DB-Handler Status code " + response.getStatus());
        }

        return null;
    }


    public void addBottle(final BottleCreateDTO bottleDTO) throws ProcessingException {

        Entity<BottleCreateDTO> entity = Entity.json(bottleDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/addBottle") // http://localhost:9999/v1/beverage/addBottle
                .request(MediaType.APPLICATION_JSON)
                .post(entity);

        if (response.getStatus() == 201) {
            log.info("Bottle successfully inserted");
        } else {
            log.warning("Error in adding bottle from DB-Handler Status code " + response.getStatus()+" " +response.getEntity());
        }


    }

    public void addCrate(final CrateCreateDTO crateDTO) throws ProcessingException {

        Entity<CrateCreateDTO> entity = Entity.json(crateDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/addCrate") // http://localhost:9999/v1/beverage/addBottle
                .request(MediaType.APPLICATION_JSON)
                .post(entity);

        if (response.getStatus() == 201) {
            log.info("Crate successfully inserted");
        } else {
            log.warning("Error in adding crate from DB-Handler Status code " + response.getStatus());
        }

    }

    public int removeCrate(final int crateId) throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/deleteCrate/" + crateId + "") // http://localhost:9999/v1/beverage/deleteCrate/
                .request(MediaType.APPLICATION_JSON)
                .delete();


        return response.getStatus();
    }

    public int removeBottle(final int bottleId) throws ProcessingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/deleteBottle/" + bottleId + "") // http://localhost:9999/v1/beverage/deleteBottle/
                .request(MediaType.APPLICATION_JSON)
                .delete();


        return response.getStatus();
    }

    public int updateCrate(final int crateId, final CrateUpdateDTO crateUpdateDTO) throws ProcessingException {

        Entity<CrateUpdateDTO> entity = Entity.json(crateUpdateDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/editCrate/" + crateId + "") // http://localhost:9999/v1/beverage/editBottle
                .request(MediaType.APPLICATION_JSON)
                .put(entity);


        return response.getStatus();
    }


    public int updateBottle(final int bottleId, final BottleUpdateDTO bottleUpdateDTO) throws ProcessingException {

        Entity<BottleUpdateDTO> entity = Entity.json(bottleUpdateDTO);
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/editBottle/" + bottleId + "") // http://localhost:9999/v1/beverage/editBottle
                .request(MediaType.APPLICATION_JSON)
                .put(entity);


        return response.getStatus();
    }

}
