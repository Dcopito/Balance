package copito.security.domain.events;

public interface EventMessageSender<T> {
    Boolean sendMessage(T messageModel);
}
