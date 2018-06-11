package com.pegaxchange.java.web.rest;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
@ApplicationPath("restservices")
public class RestSrv extends ResourceConfig {
    public RestSrv() {
        packages("com.fasterxml.jackson.jaxrs.json");
        packages("com.pegaxchange.java.web.rest");
    }
}