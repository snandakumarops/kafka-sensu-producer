# kafka-streams-quickstart-producer project
oc new-app quay.io/quarkus/ubi-quarkus-native-s2i:19.2.1~https://github.com/snandakumar87/kafka-sensu-producer
oc cancel-build bc/kafka-sensu-producer
oc patch bc/kafka-sensu-producer -p '{"spec":{"resources":{"limits":{"cpu":"5", "memory":"4Gi"}}}}'
oc start-build bc/kafka-sensu-producer
oc expose svc/kafka-sensu-producer
