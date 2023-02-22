package io.confluent.examples.clients.basicavro;

import io.confluent.union.example.avro.event.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class EventProducer {
    private static final String TOPIC = "creditevents";
    public static void main(String[] args) throws IOException {

        Properties props = Utils.buildProducerProperties();

        try (KafkaProducer<String, CreditEventV1_0> producer = new KafkaProducer<String, CreditEventV1_0>(props)) {
            int count = 0 ;
            do{
                final CreditEventV1_0 payment = createCreditEvent(count);
                final ProducerRecord<String, CreditEventV1_0> paymentRecord = new ProducerRecord<>(TOPIC, payment.getEventUniqueId().toString(), payment);
                producer.send(paymentRecord);
                Thread.sleep(1000L);
                count++;
            } while( count <= 10);

            producer.flush();
            System.out.printf("Successfully produced %d Payment messages to a topic called %s%n",count, TOPIC);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static CreditEventV1_0 createCreditEvent(int count) {

        return CreditEventV1_0.newBuilder().setEventUniqueId(count+"").setSchemaId("CreditEvent_V1_0").setEnvelope(getEnvelope(count)).setPayload(getPayload(count)).build();
    }

    private static EventPayload_v1_0 getPayload(int count) {
        return EventPayload_v1_0.newBuilder().setSchemaId("paylaod_"+count).setValue(getPayloadValue(count)).build();
    }

    private static PayloadValue getPayloadValue(int count) {
        return PayloadValue.newBuilder().setCreditAmount(count).setJourney("marketing").build();
    }

    private static EventEnvelope_v1_0 getEnvelope(int count) {
        return EventEnvelope_v1_0.newBuilder().setEventType(EventType.APPROVAL_EVENT).setSchemaId("envelope_"+count).setLineOfBusiness(lineOfBusiness.MKTG).build();

    }
}
