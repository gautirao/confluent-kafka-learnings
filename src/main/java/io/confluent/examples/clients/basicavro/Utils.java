package io.confluent.examples.clients.basicavro;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Utils {
    public static final String SCHEMA_REGISTRY_URL = "https://psrc-8kz20.us-east-2.aws.confluent.cloud";
    public static final String CONFLUENT_CLOUD_USERNAME = "MZDLPVDJJOWUK67I";
    public static final String CONFLUENT_CLOUD_API_KEY = "SmWXW+WSOk6kGdpM0NsI6vxE/410N34QtV6G5KAJonyMlDLb3JrnOF7NfshBIs1E";
    public static final String BOOTSTRAP_SERVERS = "pkc-ymrq7.us-east-2.aws.confluent.cloud:9092";
    public static final String SCHEMA_REGISTRY_API_KEY = "jT2fieiR819YKb0VbTAH1fgX/urzkBT31xMa19VBOZMlivvjpgrjpHtRbDjf9DZb";
    public static final String SCHEMA_REGISTRY_USER_NAME = "6VRQFCHS5PPL5AIG";

    public static Properties buildProducerProperties() {
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

    public static Properties buildConsumerProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(AbstractKafkaAvroSerDeConfig.USE_LATEST_VERSION, true);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-payments");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SASL_SSL");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username='" + CONFLUENT_CLOUD_USERNAME + "' password='" + CONFLUENT_CLOUD_API_KEY + "';");
        props.put(SaslConfigs.SASL_MECHANISM,"PLAIN");
        props.put(CommonClientConfigs.CLIENT_DNS_LOOKUP_CONFIG,"use_all_dns_ips");
        props.put(CommonClientConfigs.SESSION_TIMEOUT_MS_CONFIG,45000);
        props.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, false);

        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
        props.put(SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE,"USER_INFO");
        props.put(SchemaRegistryClientConfig.USER_INFO_CONFIG, SCHEMA_REGISTRY_USER_NAME + ":" + SCHEMA_REGISTRY_API_KEY);

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        return props;
    }
}
