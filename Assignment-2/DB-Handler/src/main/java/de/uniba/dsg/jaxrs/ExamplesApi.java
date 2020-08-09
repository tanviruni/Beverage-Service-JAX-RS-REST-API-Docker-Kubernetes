package de.uniba.dsg.jaxrs;

import de.uniba.dsg.jaxrs.controller.BeverageDeleteService;
import de.uniba.dsg.jaxrs.resource.BeverageDeleteResource;
import de.uniba.dsg.jaxrs.resource.BeverageGetResource;
import de.uniba.dsg.jaxrs.resource.BeveragePostResource;
import de.uniba.dsg.jaxrs.resource.BeveragePutResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();
        resources.add(BeverageGetResource.class);
        resources.add(BeveragePostResource.class);
        resources.add(BeverageDeleteResource.class);
        resources.add(BeveragePutResource.class);
        return resources;
    }
}
