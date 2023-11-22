package copito.security.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import copito.security.domain.model.events.AccountCreatedDTO;
import copito.security.infrastructure.message.TransactionMessagePublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountCreatedEvent implements EventService<AccountCreatedDTO> {
    private final TransactionMessagePublish publisher;

    @Override
    public Boolean execute(AccountCreatedDTO event) {
        try{
            log.info("Post: Id {} - Name {} - LastName {} - Type {}", event.getId(), event.getName(),
                    event.getLastName(), event.getRole());
            publisher.sendAccountEvent(event);

            log.info("Publique el mensaje");
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return true;
    }
}
