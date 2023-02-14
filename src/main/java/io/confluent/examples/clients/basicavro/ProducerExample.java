package io.confluent.examples.clients.basicavro;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.Properties;
import java.io.IOException;

public class ProducerExample {
    private static final String TOPIC = "all-types";
    public static final String SCHEMA_REGISTRY_URL = "https://psrc-8kz20.us-east-2.aws.confluent.cloud";
    public static final String CONFLUENT_CLOUD_USERNAME = "MZDLPVDJJOWUK67I";
    public static final String CONFLUENT_CLOUD_API_KEY = "SmWXW+WSOk6kGdpM0NsI6vxE/410N34QtV6G5KAJonyMlDLb3JrnOF7NfshBIs1E";
    public static final String BOOTSTRAP_SERVERS = "pkc-ymrq7.us-east-2.aws.confluent.cloud:9092";
    public static final String SCHEMA_REGISTRY_API_KEY = "jT2fieiR819YKb0VbTAH1fgX/urzkBT31xMa19VBOZMlivvjpgrjpHtRbDjf9DZb";
    public static final String SCHEMA_REGISTRY_USER_NAME = "6VRQFCHS5PPL5AIG";

    public static void main(final String[] args) throws IOException {

        Properties props = buildProducerProperties();
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

    private static Properties buildProducerProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SASL_SSL");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username='" + CONFLUENT_CLOUD_USERNAME + "' password='" + CONFLUENT_CLOUD_API_KEY + "';");
        props.put(SaslConfigs.SASL_MECHANISM,"PLAIN");
        props.put(CommonClientConfigs.CLIENT_DNS_LOOKUP_CONFIG,"use_all_dns_ips");
        props.put(CommonClientConfigs.SESSION_TIMEOUT_MS_CONFIG,45000);
        props.put(ProducerConfig.ACKS_CONFIG,"all");
        props.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, false);
        props.put(AbstractKafkaAvroSerDeConfig.USE_LATEST_VERSION, true);

        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
        props.put(SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE,"USER_INFO");
        props.put(SchemaRegistryClientConfig.USER_INFO_CONFIG, SCHEMA_REGISTRY_USER_NAME + ":" + SCHEMA_REGISTRY_API_KEY);

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        return props;
    }

    private static Refund createRefund(long i) {

        final String orderId = "id" + i;
        final double amount = 100 + i;
        return Refund.newBuilder().setRefundAmount(amount).setId(orderId).build();
    }

    private static Payment createPayment(long i) {
        final String orderId = "id" + i;
        final double amount = 100 + i;
        return Payment.newBuilder().setPaymentAmount(amount).setId(orderId).build();

    }

}
