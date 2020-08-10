package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.Configuration;
import de.uniba.dsg.jaxrs.dto.BottleDTO;
import de.uniba.dsg.jaxrs.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BeverageServiceBackend {

    private static final Logger log = Logger.getLogger("BeverageServiceBackend");
    private final String uri;

    public BeverageServiceBackend(String uri) {


        if ("".equals(uri)) {
            this.uri = "http://localhost:9999/v1";
        }
        else
            this.uri = uri;
        log.info("Using rest backend implementation with "+ this.uri);
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

    public List<Crate> getCrates() throws ProcessingException{
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.uri)
                .path("/beverage/crates") // http://localhost:9999/v1/beverage/bottles
                .request(MediaType.APPLICATION_JSON)
                .get();


        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<CrateDTO>>() {
            })
                    .stream()
                    .map(x -> {
                        return x.unmarshall();})
                    .collect(Collectors.toList());

        } else {
            log.info("Error in fetching all crates from DB-Handler Status code " + response.getStatus());
        }
        //System.out.println(returnCat.toString());
        List<Crate> list = new ArrayList<>();
        return list;
    }
}
