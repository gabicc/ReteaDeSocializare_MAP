package social_network.events;

import social_network.domain.Message;
import social_network.domain.User;

import java.time.LocalDateTime;
/*
    This class represents a message removal event that occurs in the social network.
    It extends the SocialEvent class and contains the data associated with the message removal event.
 */
public class MessageRemoveEvent extends SocialEvent<Message> {
    public MessageRemoveEvent(Message data) {
        super(data);
    }
}