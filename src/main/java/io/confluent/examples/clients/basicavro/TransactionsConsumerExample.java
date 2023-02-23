package io.confluent.examples.clients.basicavro;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.io.IOException;

public class TransactionsConsumerExample {

    private static final String TOPIC = "transactions";
    public static void main(final String[] args) throws IOException {


        Properties props = Utils.buildConsumerProperties();



        try (final KafkaConsumer<String, Payment> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC));

            while (true) {
                final ConsumerRecords<String, Payment> records = consumer.poll(Duration.ofMillis(100));
                for (final ConsumerRecord<String, Payment> record : records) {
                    final String key = record.key();
                    final Payment value = record.value();
                    System.out.printf("key = %s, value = %s%n", key, value);
                }
            }

        }
    }

}
