package com.redhat.eventproducer;

import java.util.*;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.redhat.datamodels.Check;
import com.redhat.datamodels.Example;
import com.redhat.datamodels.SensuEvents;
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

    @GET
    @Path("/sudo-bug")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void postCase() {

        String jsonString = "{\n" +
                "\t\"check\": {\n" +
                "\t\t\"command\": \"check-ansible-processes.sh -t tower\",\n" +
                "\t\t\"handlers\": [\"ansible-email\"],\n" +
                "\t\t\"high_flap_threshold\": 42,\n" +
                "\t\t\"interval\": 60,\n" +
                "\t\t\"low_flap_threshold\": 18,\n" +
                "\t\t\"publish\": true,\n" +
                "\t\t\"runtime_assets\": [\"sensu-ciss-plugins-ansible\"],\n" +
                "\t\t\"subscriptions\": [\"ansible-tower\"],\n" +
                "\t\t\"proxy_entity_name\": \"\",\n" +
                "\t\t\"check_hooks\": null,\n" +
                "\t\t\"stdin\": false,\n" +
                "\t\t\"subdue\": null,\n" +
                "\t\t\"ttl\": 100,\n" +
                "\t\t\"timeout\": 30,\n" +
                "\t\t\"round_robin\": false,\n" +
                "\t\t\"duration\": 0.112841876,\n" +
                "\t\t\"executed\": 1613416887050,\n" +
                "\t\t\"history\": [{\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010759\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010819\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010879\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010939\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010999\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011059\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011119\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011179\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011239\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011299\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011359\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011419\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011479\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011539\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011599\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011659\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011719\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011779\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011839\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011899\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011959\n" +
                "\t\t}],\n" +
                "\t\t\"issued\": 1595011959,\n" +
                "\t\t\"output\": \"\\nThe process nginx is active (running) since Fri 2020-07-17 12:48:28 EDT; 2h 4min ago\\nThe process rabbitmq-server.service is activating (start) since Fri 2020-07-17 14:52:39 EDT; 57ms ago\\nThe process supervisord is failed (Result: exit-code) since Fri 2020-07-17 12:48:28 EDT; 2h 4min ago\\nThe process memcached is failed (Result: exit-code) since Fri 2020-07-17 12:44:46 EDT; 2h 7min ago\\n\",\n" +
                "\t\t\"state\": \"failing\",\n" +
                "\t\t\"status\": 2,\n" +
                "\t\t\"total_state_change\": 0,\n" +
                "\t\t\"last_ok\": 1593203018,\n" +
                "\t\t\"occurrences\": 128,\n" +
                "\t\t\"occurrences_watermark\": 7659,\n" +
                "\t\t\"output_metric_format\": \"\",\n" +
                "\t\t\"output_metric_handlers\": null,\n" +
                "\t\t\"env_vars\": null,\n" +
                "\t\t\"metadata\": {\n" +
                "\t\t\t\"name\": \"check-ansible-processes\",\n" +
                "\t\t\t\"namespace\": \"ansible\",\n" +
                "\t\t\t\"labels\": {\n" +
                "\t\t\t\t\"sngroup\": \"CTI GL APS AUTOMATION TOOLS\",\n" +
                "\t\t\t\t\"snseverity\": \"LOW\",\n" +
                "\t\t\t\t\"sensu.io/managed_by\": \"sensuctl\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"annotations\": {\n" +
                "\t\t\t\t\"fatigue_check/interval\": \"86400\",\n" +
                "\t\t\t\t\"wiki\": \"https://cedt-confluence.nam.nsroot.net/confluence/display/C152891/FAQ+and+Resources\",\n" +
                "\t\t\t\t\"summary\": \"This check is to detect the tower process if not running\",\n" +
                "\t\t\t\t\"fatigue_check/occurrences\": \"10\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"secrets\": null\n" +
                "\t},\n" +
                "\t\"entity\": {\n" +
                "\t\t\"entity_class\": \"agent\",\n" +
                "\t\t\"system\": {\n" +
                "\t\t\t\"hostname\": \"node1\",\n" +
                "\t\t\t\"os\": \"linux\",\n" +
                "\t\t\t\"platform\": \"redhat\",\n" +
                "\t\t\t\"platform_family\": \"rhel\",\n" +
                "\t\t\t\"platform_version\": \"7.7\",\n" +
                "\t\t\t\"network\": {\n" +
                "\t\t\t\t\"interfaces\": [{\n" +
                "\t\t\t\t\t\"name\": \"lo\",\n" +
                "\t\t\t\t\t\"addresses\": [\"127.0.0.1/8\"]\n" +
                "\t\t\t\t}, {\n" +
                "\t\t\t\t\t\"name\": \"ens192\",\n" +
                "\t\t\t\t\t\"mac\": \"00:50:56:b7:36:bf\",\n" +
                "\t\t\t\t\t\"addresses\": [\"169.171.165.20/23\"]\n" +
                "\t\t\t\t}]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"arch\": \"amd64\",\n" +
                "\t\t\t\"libc_type\": \"glibc\",\n" +
                "\t\t\t\"vm_system\": \"\",\n" +
                "\t\t\t\"vm_role\": \"\",\n" +
                "\t\t\t\"cloud_provider\": \"\",\n" +
                "\t\t\t\"processes\": null\n" +
                "\t\t},\n" +
                "\t\t\"subscriptions\": [\"ansible-tower\", \"rhel-metrics\", \"rhel\", \"173176\", \"entity:node1\"],\n" +
                "\t\t\"last_seen\": 1595004285,\n" +
                "\t\t\"deregister\": false,\n" +
                "\t\t\"deregistration\": {},\n" +
                "\t\t\"user\": \"agent\",\n" +
                "\t\t\"redact\": [\"password\", \"passwd\", \"pass\", \"api_key\", \"api_token\", \"access_key\", \"secret_key\", \"private_key\", \"secret\"],\n" +
                "\t\t\"metadata\": {\n" +
                "\t\t\t\"name\": \"node1\",\n" +
                "\t\t\t\"namespace\": \"ansible\",\n" +
                "\t\t\t\"labels\": {\n" +
                "\t\t\t\t\"app_subtier\": \"ansible-tower\",\n" +
                "\t\t\t\t\"app_tier\": \"ansible-servers\",\n" +
                "\t\t\t\t\"appid\": \"173176\",\n" +
                "\t\t\t\t\"cluster\": \"ansible-Internal Project\",\n" +
                "\t\t\t\t\"datacenter\": \"gtdc\",\n" +
                "\t\t\t\t\"environment\": \"dev\",\n" +
                "\t\t\t\t\"region\": \"nam\",\n" +
                "\t\t\t\t\"sensuenv\": \"sensugo_dev\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"sensu_agent_version\": \"5.21.0\"\n" +
                "\t},\n" +
                "\t\"id\": \"newe33844\",\n" +
                "\t\"metadata\": {\n" +
                "\t\t\"namespace\": \"ansible\"\n" +
                "\t},\n" +
                "\t\"timestamp\": 1595011959\n" +
                "}\n";
        try {
            LinkedHashMap sensuCheckJson = new Gson().fromJson(jsonString,LinkedHashMap.class);
            System.out.println(sensuCheckJson.keySet());
            LinkedHashMap entityMap = new Gson().fromJson(new Gson().toJson(sensuCheckJson.get("entity")),LinkedHashMap.class);
            LinkedHashMap systemMap = new Gson().fromJson(new Gson().toJson(entityMap.get("system")),LinkedHashMap.class);

            System.out.println(systemMap);
            System.out.println(systemMap.get("hostname"));


            kafkaController.produce(jsonString,"check-sudo-bug");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/disk-usage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void diskUsage() {

        String jsonString = "{\n" +
                "\t\"check\": {\n" +
                "\t\t\"command\": \"check-ansible-processes.sh -t tower\",\n" +
                "\t\t\"handlers\": [\"ansible-email\"],\n" +
                "\t\t\"high_flap_threshold\": 42,\n" +
                "\t\t\"interval\": 60,\n" +
                "\t\t\"low_flap_threshold\": 18,\n" +
                "\t\t\"publish\": true,\n" +
                "\t\t\"runtime_assets\": [\"sensu-ciss-plugins-ansible\"],\n" +
                "\t\t\"subscriptions\": [\"ansible-tower\"],\n" +
                "\t\t\"proxy_entity_name\": \"\",\n" +
                "\t\t\"check_hooks\": null,\n" +
                "\t\t\"stdin\": false,\n" +
                "\t\t\"subdue\": null,\n" +
                "\t\t\"ttl\": 100,\n" +
                "\t\t\"timeout\": 30,\n" +
                "\t\t\"round_robin\": false,\n" +
                "\t\t\"duration\": 0.112841876,\n" +
                "\t\t\"executed\": 1613416887050,\n" +
                "\t\t\"history\": [{\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010759\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010819\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010879\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010939\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595010999\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011059\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011119\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011179\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011239\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011299\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011359\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011419\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011479\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011539\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011599\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011659\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011719\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011779\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011839\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011899\n" +
                "\t\t}, {\n" +
                "\t\t\t\"status\": 2,\n" +
                "\t\t\t\"executed\": 1595011959\n" +
                "\t\t}],\n" +
                "\t\t\"issued\": 1595011959,\n" +
                "\t\t\"output\": \"\\nThe process nginx is active (running) since Fri 2020-07-17 12:48:28 EDT; 2h 4min ago\\nThe process rabbitmq-server.service is activating (start) since Fri 2020-07-17 14:52:39 EDT; 57ms ago\\nThe process supervisord is failed (Result: exit-code) since Fri 2020-07-17 12:48:28 EDT; 2h 4min ago\\nThe process memcached is failed (Result: exit-code) since Fri 2020-07-17 12:44:46 EDT; 2h 7min ago\\n\",\n" +
                "\t\t\"state\": \"failing\",\n" +
                "\t\t\"status\": 2,\n" +
                "\t\t\"total_state_change\": 0,\n" +
                "\t\t\"last_ok\": 1593203018,\n" +
                "\t\t\"occurrences\": 128,\n" +
                "\t\t\"occurrences_watermark\": 7659,\n" +
                "\t\t\"output_metric_format\": \"\",\n" +
                "\t\t\"output_metric_handlers\": null,\n" +
                "\t\t\"env_vars\": null,\n" +
                "\t\t\"metadata\": {\n" +
                "\t\t\t\"name\": \"check-ansible-processes\",\n" +
                "\t\t\t\"namespace\": \"ansible\",\n" +
                "\t\t\t\"labels\": {\n" +
                "\t\t\t\t\"sngroup\": \"CTI GL APS AUTOMATION TOOLS\",\n" +
                "\t\t\t\t\"snseverity\": \"LOW\",\n" +
                "\t\t\t\t\"sensu.io/managed_by\": \"sensuctl\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"annotations\": {\n" +
                "\t\t\t\t\"fatigue_check/interval\": \"86400\",\n" +
                "\t\t\t\t\"wiki\": \"https://cedt-confluence.nam.nsroot.net/confluence/display/C152891/FAQ+and+Resources\",\n" +
                "\t\t\t\t\"summary\": \"This check is to detect the tower process if not running\",\n" +
                "\t\t\t\t\"fatigue_check/occurrences\": \"10\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"secrets\": null\n" +
                "\t},\n" +
                "\t\"entity\": {\n" +
                "\t\t\"entity_class\": \"agent\",\n" +
                "\t\t\"system\": {\n" +
                "\t\t\t\"hostname\": \"node1\",\n" +
                "\t\t\t\"os\": \"linux\",\n" +
                "\t\t\t\"platform\": \"redhat\",\n" +
                "\t\t\t\"platform_family\": \"rhel\",\n" +
                "\t\t\t\"platform_version\": \"7.7\",\n" +
                "\t\t\t\"network\": {\n" +
                "\t\t\t\t\"interfaces\": [{\n" +
                "\t\t\t\t\t\"name\": \"lo\",\n" +
                "\t\t\t\t\t\"addresses\": [\"127.0.0.1/8\"]\n" +
                "\t\t\t\t}, {\n" +
                "\t\t\t\t\t\"name\": \"ens192\",\n" +
                "\t\t\t\t\t\"mac\": \"00:50:56:b7:36:bf\",\n" +
                "\t\t\t\t\t\"addresses\": [\"169.171.165.20/23\"]\n" +
                "\t\t\t\t}]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"arch\": \"amd64\",\n" +
                "\t\t\t\"libc_type\": \"glibc\",\n" +
                "\t\t\t\"vm_system\": \"\",\n" +
                "\t\t\t\"vm_role\": \"\",\n" +
                "\t\t\t\"cloud_provider\": \"\",\n" +
                "\t\t\t\"processes\": null\n" +
                "\t\t},\n" +
                "\t\t\"subscriptions\": [\"ansible-tower\", \"rhel-metrics\", \"rhel\", \"173176\", \"entity:node1\"],\n" +
                "\t\t\"last_seen\": 1595004285,\n" +
                "\t\t\"deregister\": false,\n" +
                "\t\t\"deregistration\": {},\n" +
                "\t\t\"user\": \"agent\",\n" +
                "\t\t\"redact\": [\"password\", \"passwd\", \"pass\", \"api_key\", \"api_token\", \"access_key\", \"secret_key\", \"private_key\", \"secret\"],\n" +
                "\t\t\"metadata\": {\n" +
                "\t\t\t\"name\": \"node1\",\n" +
                "\t\t\t\"namespace\": \"ansible\",\n" +
                "\t\t\t\"labels\": {\n" +
                "\t\t\t\t\"app_subtier\": \"ansible-tower\",\n" +
                "\t\t\t\t\"app_tier\": \"ansible-servers\",\n" +
                "\t\t\t\t\"appid\": \"173176\",\n" +
                "\t\t\t\t\"cluster\": \"ansible-Internal Project\",\n" +
                "\t\t\t\t\"datacenter\": \"gtdc\",\n" +
                "\t\t\t\t\"environment\": \"dev\",\n" +
                "\t\t\t\t\"region\": \"nam\",\n" +
                "\t\t\t\t\"sensuenv\": \"sensugo_dev\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"sensu_agent_version\": \"5.21.0\"\n" +
                "\t},\n" +
                "\t\"id\": \"newe33844\",\n" +
                "\t\"metadata\": {\n" +
                "\t\t\"namespace\": \"ansible\"\n" +
                "\t},\n" +
                "\t\"timestamp\": 1595011959\n" +
                "}\n";
        try {
            LinkedHashMap sensuCheckJson = new Gson().fromJson(jsonString,LinkedHashMap.class);
            System.out.println(sensuCheckJson.keySet());
            LinkedHashMap entityMap = new Gson().fromJson(new Gson().toJson(sensuCheckJson.get("entity")),LinkedHashMap.class);
            LinkedHashMap systemMap = new Gson().fromJson(new Gson().toJson(entityMap.get("system")),LinkedHashMap.class);

            System.out.println(systemMap);
            System.out.println(systemMap.get("hostname"));


            kafkaController.produce(jsonString,"check-disk-usage");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
