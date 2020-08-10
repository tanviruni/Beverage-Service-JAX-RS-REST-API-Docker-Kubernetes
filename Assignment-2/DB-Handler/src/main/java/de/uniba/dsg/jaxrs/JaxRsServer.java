package de.uniba.dsg.jaxrs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import com.sun.net.httpserver.HttpServer;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class JaxRsServer {

    public static void main(String[] args) throws IOException {
        String serverUri = Configuration.getServerUri();

        URI baseUri = UriBuilder.fromUri(serverUri).build();
        ResourceConfig config = ResourceConfig.forApplicationClass(ExamplesApi.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("DB-Handler server is ready to serve JAX-RS requests...");
        System.out.println("Press any key to exit...");
        System.in.read();



    }
}
