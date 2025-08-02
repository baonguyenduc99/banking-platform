#!/bin/sh
sleep 10  # Wait for Kafka to start
kafka-topics --bootstrap-server kafka:9092 --create --topic auth_events --partitions 3 --replication-factor 1
kafka-topics --bootstrap-server kafka:9092 --create --topic payment_events --partitions 3 --replication-factor 1