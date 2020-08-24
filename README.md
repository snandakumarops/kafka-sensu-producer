# Sensu Event Producer
This component is a mock event producer which emulates the actual sensu events  coming in to the event stream.
```
oc new-app registry.access.redhat.com/ubi8/openjdk-11:latest~https://github.com/snandakumar87/kafka-sensu-producer
oc expose svc/kafka-sensu-producer
```