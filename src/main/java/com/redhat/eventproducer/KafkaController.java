package com.redhat.eventproducer;
import com.google.gson.Gson;
import com.redhat.datamodels.Check;
import com.redhat.datamodels.Example;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@ApplicationScoped
public class KafkaController {

    private FlowableEmitter<KafkaMessage<String, String>> emitter;

    private Flowable<KafkaMessage<String, String>> outgoingStream;

    @PostConstruct
    void init() {
        outgoingStream = Flowable.create(emitter -> this.emitter = emitter, BackpressureStrategy.BUFFER);
    }

    public void produce(String message, String type) {

        Example check = new Gson().fromJson(message, Example.class);
        check.getCheck().setExecuted(new Date().getTime());
        Random rand = new Random();
        check.setId("user"+rand.nextInt(1000));
        System.out.println(check.getEntity().getSystem().getHostname());
        check.getCheck().getMetadata().setName(type);
        emitter.onNext(KafkaMessage.of(check.getEntity().getSystem().getHostname(), new Gson().toJson(check)));
    }

    @PreDestroy
    void dispose() {
        emitter.onComplete();
    }

    @Outgoing("sensu-failure")
    Publisher<KafkaMessage<String, String>> produceKafkaMessage() {
        return outgoingStream;
    }


}