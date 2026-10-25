package social_network.events;

import social_network.domain.User;

import java.time.LocalDateTime;
/*
    This class represents a user removal event that occurs in the social network.
    It extends the SocialEvent class and contains the data associated with the user removal event.
 */
public class UserRemoveEvent extends SocialEvent<User> {
    public UserRemoveEvent(User data) {
        super(data);
    }
}