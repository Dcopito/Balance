package copito.security.domain.model.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountCreatedDTO extends Event {
    private String id;
    private String role;
    private String name;
    private String lastName;
}
