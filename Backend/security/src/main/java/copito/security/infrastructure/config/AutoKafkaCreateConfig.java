package copito.security.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class AutoKafkaCreateConfig {
    @Value("${spring.kafka.template.default-topic}")
    String topicname;
    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapAddress;

    @Bean
    public NewTopic transactionEvent(){
        return TopicBuilder.name(topicname)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> configProps =new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapAddress
        );

        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );

        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
