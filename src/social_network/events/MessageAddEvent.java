package social_network.events;

import social_network.domain.Message;
import social_network.domain.User;

import java.time.LocalDateTime;
/**
 * This class represents an event that occurs when a message is added to the social network.
 * It extends the SocialEvent class and contains the data associated with the message that was added.
 */
public class MessageAddEvent extends SocialEvent<Message> {
    public MessageAddEvent(Message data) {
        super(data);
    }
}