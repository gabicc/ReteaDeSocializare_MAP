package social_network.events;

import social_network.domain.User;

import java.time.LocalDateTime;
/*
    This class represents a user update event that occurs in the social network.
    It extends the SocialEvent class and contains the data associated with the user update event.
 */
public class UserUpdateEvent extends SocialEvent<User> {
    public UserUpdateEvent(User data) {
        super(data);
    }
}