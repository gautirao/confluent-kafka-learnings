package io.confluent.examples.clients.basicavro;

import com.natwest.event.v1_0.CreditEvent;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class EventConsumer {

    private static final String TOPIC="creditevents";

    public static void main(String[] args){
    Properties props = Utils.buildConsumerProperties();

        try (final KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC));

            while (true) {
                final ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
                System.out.println("Retrieved "+records.count()+" records");
                for (final ConsumerRecord<String, GenericRecord> record : records) {

                    if(record.value() != null){
                        if(record.value() instanceof com.natwest.event.v1_0.CreditEvent){

                            printV1Event(record);
                        }else{
                            printV2Event(record);
                        }

                    }

                }
                consumer.commitAsync();
            }

        }
    }

    private static void printV1Event(ConsumerRecord<String, GenericRecord> record) {

        com.natwest.event.v1_0.CreditEvent v1Event = (com.natwest.event.v1_0.CreditEvent) record.value();
        System.out.println("v1 Envelope Event :"+ v1Event.getEnvelope().getEventType().toString());
    }

    private static void printV2Event(ConsumerRecord<String, GenericRecord> record) {
        com.natwest.event.v1_1.CreditEvent v2Event = (com.natwest.event.v1_1.CreditEvent) record.value();
        System.out.println("v1 Envelope Event :"+ v2Event.getEnvelope().getEventType().toString());
    }

}
