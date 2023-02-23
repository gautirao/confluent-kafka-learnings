package io.confluent.examples.clients.basicavro;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.io.IOException;

public class TransactionsProducerExample {
    private static final String TOPIC = "all-types";

    public static void main(final String[] args) throws IOException {

        Properties props = Utils.buildProducerProperties();
//:TODO : test against object name changes during schema version increments
        //TODO: investigate using schema registry as a S3 bucket to store json schemas for consumer to use
        System.out.println("Props : " + props);
        try (KafkaProducer<String, Payment> producer = new KafkaProducer<String, Payment>(props)) {
            int count = 0 ;
                    do{
                        final Payment payment = createPayment(count);
                        final ProducerRecord<String, Payment> paymentRecord = new ProducerRecord<>(TOPIC, payment.getId().toString(), payment);
                        producer.send(paymentRecord);
                        Thread.sleep(1000L);
                        count++;
                    } while( count <= 10);

            producer.flush();
            System.out.printf("Successfully produced %d Payment messages to a topic called %s%n",count, TOPIC);

        } catch (final Exception e) {
            e.printStackTrace();
        }

        try (KafkaProducer<String, Refund> producer = new KafkaProducer<String, Refund>(props)) {
            int count = 0 ;
            do {
                final Refund refund = createRefund(count);
                final ProducerRecord<String, Refund> refundRecord = new ProducerRecord<>(TOPIC, refund.getId().toString(), refund);
                producer.send(refundRecord);
                Thread.sleep(1000L);
                count++;
            } while( count <= 10);

            producer.flush();
            System.out.printf("Successfully produced %d Refund messages to a topic called %s%n",count, TOPIC);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private static Refund createRefund(long i) {

        final String orderId = "id" + i;
        final double amount = 100 + i;
        return Refund.newBuilder().setRefundAmount(amount).setId(orderId).setRefundReason("item returned").build();
    }

    private static Payment createPayment(long i) {
        final String orderId = "id" + i;
        final double amount = 100 + i;
        return Payment.newBuilder().setPaymentAmount(amount).setId(orderId).build();

    }

}
