package de.uniba.dsg.jaxrs;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class JaxRsServer {

    public static void main(String[] args) throws IOException {
        String serverUri = Configuration.getServerUri();

        URI baseUri = UriBuilder.fromUri(serverUri).build();
        ResourceConfig config = ResourceConfig.forApplicationClass(ExamplesApi.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Management-Service server ready to serve your JAX-RS requests...");


    }
}
