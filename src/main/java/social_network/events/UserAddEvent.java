package social_network.events;

import social_network.domain.User;

import java.time.LocalDateTime;
/*
    This class represents a user addition event that occurs in the social network.
    It extends the SocialEvent class and contains the data associated with the user addition event.
 */
public class UserAddEvent extends SocialEvent<User> {
    public UserAddEvent(User data) {
        super(data);
    }
}
