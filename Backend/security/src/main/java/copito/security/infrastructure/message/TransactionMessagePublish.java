package copito.security.infrastructure.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import copito.security.domain.model.events.AccountCreatedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionMessagePublish {

    @Value("${spring.kafka.template.default-topic}")
    String topicName;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public CompletableFuture<SendResult<String, String>> sendAccountEvent(AccountCreatedDTO messageModel) throws JsonProcessingException{
        String key = messageModel.getId();
        String value = objectMapper.writeValueAsString(messageModel);
        ProducerRecord<String, String> producerRecord = buildProducerRecord(key, value, topicName);

        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(producerRecord);

        completableFuture.whenComplete((result, ex) ->{
            if (ex != null){
                handleFailure(key, value, ex);
            } else {
                try{
                    handleSuccess(key, value, result);
                }catch (JsonProcessingException e){
                    e.printStackTrace();
                }
            }
        });

        return completableFuture;

    }

    private ProducerRecord<String, String> buildProducerRecord(String key, String value, String topic){
        List<Header> recordHeaders = List.of(new RecordHeader("account-subscription-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null , key, value, recordHeaders);
    }

    private void handleFailure(String key, String value, Throwable ex){
        log.error("Error: send message and the error is {}", ex.getMessage());
        try{

        }catch (Throwable e){
            log.error("Error on Onfailure {}", e.getMessage());
        }
    }

    private void handleSuccess(String key, String value, SendResult<String, String> result) throws JsonMappingException, JsonProcessingException{
        log.info("Message sent successfully for the key: {} and value {}, partition is {}", key, value, result.getRecordMetadata().partition());
    }
}
