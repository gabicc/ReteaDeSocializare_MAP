package social_network.events;

import social_network.domain.Friendship;
import social_network.domain.User;

import java.time.LocalDateTime;
/*
    This class represents an event that occurs when a friendship is added in the social network. It extends the SocialEvent class and contains the data associated with the friendship that was added.

    @param data The Friendship object that was added to the social network.
 */
public class FriendshipAddEvent extends SocialEvent<Friendship> {
    public FriendshipAddEvent(Friendship data) {
        super(data);
    }
}