package com.redhat.eventproducer;

import java.util.*;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.redhat.datamodels.Check;
import com.redhat.datamodels.Example;
import io.vertx.core.json.Json;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;

@Path("/events")
@ApplicationScoped
public class EventProducer {
    @Inject
    KafkaController kafkaController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void postCase(String json,@javax.ws.rs.PathParam("custId") String customerId) {

        try {
            Example sensuCheckJson = new Gson().fromJson(json,Example.class);
            kafkaController.produce(sensuCheckJson.getEntity().getSystem().getHostname(),json);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
