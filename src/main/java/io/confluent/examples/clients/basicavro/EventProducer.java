package io.confluent.examples.clients.basicavro;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class EventProducer {
    private static final String TOPIC = "creditevents";
    public static final int MESSAGE_COUNT = 4;

    public static void main(String[] args) throws IOException {

        Properties props = Utils.buildProducerProperties();

        try (KafkaProducer<String, com.natwest.event.v1_0.CreditEvent> producer = new KafkaProducer<String, com.natwest.event.v1_0.CreditEvent>(props)) {
            int count = 0 ;
            do{
                final com.natwest.event.v1_0.CreditEvent crediteventv1_0 = V1_0_Utils.createCreditEvent(count);
                final ProducerRecord<String, com.natwest.event.v1_0.CreditEvent> event = new ProducerRecord<>(TOPIC, crediteventv1_0.getEventUniqueId().toString(), crediteventv1_0);
                producer.send(event);
                Thread.sleep(1000L);
                count++;
            } while( count <= MESSAGE_COUNT);

            producer.flush();
            System.out.printf("Successfully produced %d Payment messages to a topic called %s%n",count, TOPIC);

        } catch (final Exception e) {
            e.printStackTrace();
        }


        try (KafkaProducer<String, com.natwest.event.v1_1.CreditEvent> producer = new KafkaProducer<String, com.natwest.event.v1_1.CreditEvent>(props)) {
            int count = 0 ;
            do{
                final com.natwest.event.v1_1.CreditEvent payment = V1_1_Utils.createCreditEvent(count);
                final ProducerRecord<String, com.natwest.event.v1_1.CreditEvent> paymentRecord = new ProducerRecord<>(TOPIC, payment.getEventUniqueId().toString(), payment);
                producer.send(paymentRecord);
                Thread.sleep(1000L);
                count++;
            } while( count <= MESSAGE_COUNT);

            producer.flush();
            System.out.printf("Successfully produced %d Payment messages to a topic called %s%n",count, TOPIC);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
